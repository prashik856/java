package com.prashik;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.vault.VaultsClient;
import com.oracle.bmc.vault.model.CreateSecretDetails;
import com.oracle.bmc.vault.model.SecretSummary;
import com.oracle.bmc.vault.model.Base64SecretContentDetails;
import com.oracle.bmc.vault.model.SecretContentDetails;
import com.oracle.bmc.vault.model.ScheduleSecretDeletionDetails;
import com.oracle.bmc.vault.requests.CancelSecretDeletionRequest;
import com.oracle.bmc.vault.requests.CreateSecretRequest;
import com.oracle.bmc.vault.requests.ListSecretsRequest;
import com.oracle.bmc.vault.requests.ScheduleSecretDeletionRequest;
import com.oracle.bmc.vault.responses.CancelSecretDeletionResponse;
import com.oracle.bmc.vault.responses.CreateSecretResponse;
import com.oracle.bmc.vault.responses.ListSecretsResponse;
import com.oracle.bmc.vault.responses.ScheduleSecretDeletionResponse;
import io.helidon.common.Base64Value;
import io.helidon.config.Config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static io.helidon.config.ConfigSources.file;

public class Main {
    public static void main(String[] args){
        /* Create a service client */
        try {
            // get config directory
            Path configsDir = Paths.get("/Users/prraut/Documents/Github/java/vault-test");
            final String baseConfigFile = configsDir.resolve("base-config.yaml").toFile().getAbsolutePath();
            Config config = Config.builder()
                    .addSource(file(baseConfigFile))
                    .build();
            String vaultRegion = config.get("oci.region").asString().get();
            System.out.println("Vault Region: " + vaultRegion);
            String compartmentOcid = config.get("oci.compartment").asString().get();
            System.out.println("Compartment id: " + compartmentOcid);
            String vaultOcid = config.get("oci.vault.vaultId").asString().get();
            System.out.println("Vault id: " + vaultOcid);
            String keyOcid = config.get("oci.vault.keyId").asString().get();
            System.out.println("Key id: " + keyOcid);

            // hardcoded values
            String secretName = "prashik-test__atp_monolith_user";
            String secretDescription = "Tenant monolith user secret";

            final ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
            final AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
            System.out.println("Creating vaults client.");
            VaultsClient client = VaultsClient.builder().region("us-ashburn-1").build(provider);
            System.out.println("Created vaults client");

            System.out.println("Creating list secret request");
            HashMap<String, Object> metadata = new HashMap<>();
            metadata.put("description", secretDescription);
            metadata.put("secretName", secretName);
            String userName = "TENANT_PRASHIK_TEST";
            String password = "Welcome@123";
            String jsonString = "{\"username\": \"" + userName + "\", \"password: \"" + password + "\" }";
            String secretState = "";
            String secretOcid = "";

            ListSecretsResponse listSecretsResponse = getSecret(client, secretName, compartmentOcid, vaultOcid);
            List<SecretSummary> secretSummaries = listSecretsResponse.getItems();

            if(secretSummaries.isEmpty()) {
                System.out.println("Did not find any secret named " + secretName);

                System.out.println("Creating a new secret in the vault");
                // Create Secret
                /* Send request to the Client */
                CreateSecretResponse createSecretResponse = createSecret(client,
                        compartmentOcid, secretDescription, keyOcid, metadata, jsonString, secretName, vaultOcid);

                System.out.println("Created secret id: " + createSecretResponse.getSecret().getId());
                System.out.println("Secret's current lifecycle state: " + createSecretResponse.getSecret().getLifecycleState().getValue());
            } else {
                secretState = secretSummaries.get(0).getLifecycleState().getValue();
                secretOcid = secretSummaries.get(0).getId();

                System.out.println("Found secrets with name " + secretName);
                // Should always be 1
                System.out.println("Number of secrets returned: " + secretSummaries.size());
                // Check the current state
                System.out.println("Secret lifecycle state: " + secretState);

                if(Objects.equals(secretState, "ACTIVE")) {
                    System.out.println("Secret is in active state. Nothing to do.");
                } else if(Objects.equals(secretState, "PENDING_DELETION")) {
                    System.out.println("Secret is in " + secretState + " state. Cancelling pending deletion.");
                    /* Send request to the Client */
                    CancelSecretDeletionResponse cancelSecretDeletionResponse = cancelPendingDeletion(client, secretOcid);
                    System.out.println("Cancelled deletion of secret.");
                }

                // Delete secret
                System.out.println("Deleting secret " + secretName);

                /* Send request to the Client */
                ScheduleSecretDeletionResponse scheduleSecretDeletionResponse = deleteSecret(client, userName, secretOcid);
                System.out.println("Done.");
            }

        } catch (Exception e) {
            System.out.println("Error creating vaults client. Stacktrace: ");
            e.printStackTrace();
        }
    }

    /**
     * This returns the secret deletion time based on the username.
     *
     * @param username the input username of the secret
     * @return date
     */
    private static Date getDeleteTime(String username) {
        Date deleteTime;
        if(username.contains("_DEV") || username.contains("_LITE")) {
            deleteTime = Date.from(Instant.now().plus(1, ChronoUnit.DAYS)
                    .plus(1, ChronoUnit.MINUTES));
        } else {
            deleteTime = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        }

        return deleteTime;
    }

    /**
     * Function to get the list of secret from vault.
     *
     * @param client the vault client
     * @param secretName the name of the secret to search for
     * @param compartmentOcid the OCID of the compartment where vault is present
     * @param vaultOcid the OCID of the vault
     * @return listSecretsResponse from the client
     */
    private static ListSecretsResponse getSecret(VaultsClient client, String secretName,
                                  String compartmentOcid, String vaultOcid) {
        ListSecretsRequest listSecretsRequest = ListSecretsRequest.builder()
                .compartmentId(compartmentOcid)
                .name(secretName)
                .limit(10)
                .sortBy(ListSecretsRequest.SortBy.Name)
                .vaultId(vaultOcid)
                .build();

        System.out.println("Getting the response from the vault");
        return client.listSecrets(listSecretsRequest);
    }

    /**
     * Function to create secret into the vault.
     *
     * @param client the vault client
     * @param compartmentOcid the OCID of the compartment where vault is present
     * @param secretDescription the description of the secret to be made
     * @param keyOcid the OCID of the vault key to be used
     * @param metadata the metadata of the secret to be created
     * @param jsonString the content of the secret to be created
     * @param secretName the name of the secret to search for
     * @param vaultOcid the OCID of the vault
     * @return createSecretsResponse from the client
     */
    private static CreateSecretResponse createSecret(VaultsClient client,
                                     String compartmentOcid,
                                     String secretDescription,
                                     String keyOcid,
                                     HashMap<String, Object> metadata,
                                     String jsonString,
                                     String secretName,
                                     String vaultOcid) {
        // Create Secret
        CreateSecretDetails createSecretDetails = CreateSecretDetails.builder()
                .compartmentId(compartmentOcid)
                .description(secretDescription)
                .keyId(keyOcid)
                .metadata(metadata)
                .secretContent(Base64SecretContentDetails.builder()
                        .content(Base64Value.create(jsonString).toBase64())
                        .stage(SecretContentDetails.Stage.Current).build())
                .secretName(secretName)
                .vaultId(vaultOcid)
                .build();

        CreateSecretRequest createSecretRequest = CreateSecretRequest.builder()
                .createSecretDetails(createSecretDetails)
                .build();

        /* Send request to the Client */
        return client.createSecret(createSecretRequest);
    }

    /**
     * Function to create secret into the vault.
     *
     * @param client the vault client
     * @param secretOcid the OCID of the secret to be deleted
     * @return createSecretsResponse from the client
     */
    private static CancelSecretDeletionResponse cancelPendingDeletion(VaultsClient client,
                                                                      String secretOcid) {
        // Cancel Pending deletion.
        CancelSecretDeletionRequest cancelSecretDeletionRequest = CancelSecretDeletionRequest.builder()
                .secretId(secretOcid)
                .build();

        /* Send request to the Client */
        return client.cancelSecretDeletion(cancelSecretDeletionRequest);
    }

    /**
     * Function to schedule secret deletion in the vault.
     *
     * @param client the vault client
     * @param userName the userName of the tenant
     * @param secretOcid the OCID of the secret to be deleted
     * @return createSecretsResponse from the client
     */
    private static ScheduleSecretDeletionResponse deleteSecret(VaultsClient client, String userName,
                                                               String secretOcid) {
        /* Create a request and dependent object(s). */
        ScheduleSecretDeletionDetails scheduleSecretDeletionDetails = ScheduleSecretDeletionDetails.builder()
                .timeOfDeletion(getDeleteTime(userName))
                .build();

        ScheduleSecretDeletionRequest scheduleSecretDeletionRequest = ScheduleSecretDeletionRequest.builder()
                .secretId(secretOcid)
                .scheduleSecretDeletionDetails(scheduleSecretDeletionDetails)
                .build();

        /* Send request to the Client */
        return client.scheduleSecretDeletion(scheduleSecretDeletionRequest);
    }
}
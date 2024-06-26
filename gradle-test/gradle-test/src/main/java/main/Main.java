package main;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        // tests
        String[] tenantNamespaces = {"prashik-test",
                "prashik-prod",
                "prashik-test2",
                "prashik-dev",
                "prashik-lite",
                "prashik",
                "prashik-blah",
                "prashik-blah-blah"
        };

        for(String namespace: tenantNamespaces) {
            System.out.println("Tenant Namespace: " + namespace);
            System.out.println("Tenant ID: " + computeId(namespace));
            System.out.println("");
        }

        // test
        String siKind = "test";
        String[] hostnames = {
                "prashik-dev.internal.iot.oraclecloud.com",
                "prashik-lite.internal.iot.oraclecloud.com",

                "prashik-test.staging.iot.oraclecloud.com",
                "prashik-test2.staging.iot.oraclecloud.com",
                "prashik-test3.staging.iot.oraclecloud.com",
                "prashik-prod.staging.iot.oraclecloud.com",

                "prashik-test.iot.oraclecloud.com",
                "prashik-test2.iot.oraclecloud.com",
                "prashik-test3.iot.oraclecloud.com",
                "prashik-prod.iot.oraclecloud.com"
        };

        for(String hostname: hostnames) {
            String kind = "";
            String tenantNamespace = "";
            if (siKind.equals("test")) {
                // for test/test2/test3 kind

                // tenantNamespace = <accountName>-<test/test2/test3>
                tenantNamespace = hostname.toLowerCase().split("\\.")[0];
                kind = tenantNamespace.split("-")[1];
            } else {
                // for lite, dev, prod kind
                kind = siKind.toLowerCase();
            }
            System.out.println("Hostname: " + hostname);
            System.out.println("Kind: " + kind);
            System.out.println("");
        }

    }

    private static String computeId(String tenantNamespace) {
        String[] tenantNamespaceSplit = tenantNamespace.split("-");
        // tenant namespace which we got is wrong.
        if (tenantNamespaceSplit.length != 2) {
            // I am expecting <accountName>-<kind>, all others are invalid.
            System.out.println("The tenant namespace " + tenantNamespace + " does not follow proper convention.");
            return tenantNamespace;
        }
        // if tenant namespace has prod, remove prod, else return the tenantNamespace
        if (Objects.equals(tenantNamespaceSplit[1], "prod")) {
            return tenantNamespaceSplit[0];
        }
        return tenantNamespace;
    }
}

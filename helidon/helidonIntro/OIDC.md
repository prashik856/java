# OIDC

This guide describes the steps required to protect your whole application or a specific area with Open ID Connect (OIDC) security. OIDC is a secure mechanism for an application to contact an identity service. It’s built on top of OAuth 2.0 and provides full-fledged authentication and authorization protocols.

## Key Cloak Download
https://www.keycloak.org/downloads

Start in dev mode:
```shell
cd keycloak-11.0.2/bin
./kc.sh start-dev

# 2024-06-28 17:18:28,179 INFO  [io.quarkus] (main) Keycloak 25.0.1 on JVM (powered by Quarkus 3.8.5) started in 4.601s. Listening on: http://0.0.0.0:8080. Management interface listening on http://0.0.0.0:9000.
```

## Create an Admin User
Open http://localhost:8080/ to create admin user.
Example: 
```shell
user: admin
pass: Welcome1
```

## Setup Keycloak
http://localhost:8080/admin/master/console/

## Create a Realm
A realm is the place where groups of applications, and their environment, can be created. It gathers :
```
    One or several applications

    One or several users

    Sessions

    Events

    Clients and their scopes
```

To create a new realm to manage your application:
```
    Open Keycloak admin console http://localhost:8080/admin/master/console/.
    Hover the mouse over the dropdown in the top-left corner where it says Master, and press Add realm.
    Fill the form by adding the realm name, myRealm for example.
    Click on Create to create the new realm. 
```

## Create a User
Initially there are no users in a new realm. An unlimited number of user can be created per realm. A realm contains resources such as client which can be accessed by users.

To create a new user:
```
    Open the Keycloak admin console: http://localhost:8080/admin/master/console/
    Click on Users in the left menu
    Press Add user
    Fill the form (Username is the only mandatory field) with this value Username: myUser
    Click Save

```

A new user is just created, but it needs a password to be able to log in. To initialize it, do this:
```
     Click on Credentials at the top of the page, under Myuser.
    Fill Password and Password confirmation with the user password of your choice.
    If the Temporary field is set to ON, the user has to update password on next login. Click ON to make it OFF and prevent it.
    Press Set Password.
    A pop-up window is popping off. Click on Set Password to confirm the new password. 
```

To verify that the new user is created correctly:
```
     Open the Keycloak account console: http://localhost:8080/auth/realms/myRealm/account.
    Login with myUser and password chosen earlier. 
    # I could not do this.
```

## Create a Client
To create your first client:
```
    Open the Keycloak admin console: http://localhost:8080/admin/master/console/#/prashikRealm.
    Make sure the current realm is myRealm and not Master.
    Navigate to the left menu, into configure section, click on Clients. 
    This window displays a table with every client from the realm.
    Click on Create.
    Fill the following:
        Client ID : myClientID
        Client Protocol : openid-connect
    Press Save
        Modify Access type : confidential
        Update Valid Redirect URIs : http://localhost:7987/*
        Click on + to add the new URI. 
    Click on Save. 
```

A new tab named Credentials is created. Click on it to access this new tab.

    Select Client Authenticator : Client ID and Secret

    Click on generate secret to generate client secret.

Keycloak is now configured and ready. Keep keycloak running on your terminal and open a new tab to set up Helidon.

## Setup Helidon: Update Project Dependencies
Add the following dependency to pom.xml:
```xml
<dependency>
    <groupId>io.helidon.security.providers</groupId>
    <artifactId>helidon-security-providers-oidc</artifactId>
</dependency>
```

## Add OIDC Security Properties
The OIDC security provider configuration can be joined to helidon configuration file. This file is located here: src/main/resources/application.yaml. It can be easily used to configure the web server without modifying application code.

Replace the old port into application.yaml
```yaml
server:
  port: "{Your-new-port}"

frontend-uri: "http://localhost:{Your-new-port}"
```

## Configure Web Server
Add the following to the Main#routing method
```java
Config config = Config.global();
routing.addFeature(OidcFeature.create(config)); 
```

That code is extracting security properties from application.yaml into two steps. First the Security instance is used to bootstrap security, so the SecurityFeature instance can integrate security into Web Server. Then, OidcFeature instance registers the endpoint to which OIDC redirects browser after a successful login.

Build the Application and run it.

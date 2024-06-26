# Config

## Configuration Formats
```
    Java Property (.properties)

    JSON (.json)

    YAML (.yaml)

    HOCON (.conf)
```

## Default Configuration
We can provide our config in `application.yaml` file. 
This file is present in `src/main/resources`.

## Source Precedence for Default Configuration
By default, Helidon will use the following sources in precedence order:
```
    Java system properties
    Environment variables
    Configuration specified in application.yaml
```

Server port: `server.port`

Change app.greeting in resources/application.yaml as follows:
```yaml
    app:
      greeting: HelloFrom-application.yaml
```

Build the application
```shell
./gradlew clean build assemble
```

Run the application
```shell
java -jar build/libs/helidonIntro.jar
```

Run the curl command in a new terminal window and check the response:
```shell
curl http://localhost:8080/greet
```

The new app.greeting value in application.yaml is used.
```json
{
"message": "HelloFrom-application.yaml World!"
}
```

## Environment Variable Override
An environment property has a higher precedence than application.yaml.

Set the environment variable and restart the application:
```shell
export APP_GREETING=HelloFromEnvironment
java -jar build/libs/helidonIntro.jar
```

Invoke the endpoint below and check the response:
```shell
curl http://localhost:8080/greet

```

The environment property took precedence over application.yaml.
```json
{
"message": "HelloFromEnvironment World!"
}
```

## System Property Override
A system variable has a higher precedence than the environment property.
Restart the application with a system property. The APP_GREETING environment variable is still set:

```shell
export APP_GREETING=HelloFromEnvironment
java -Dapp.greeting="HelloFromSystemProperty" -jar build/libs/helidonIntro.jar
```

Invoke the endpoint below and check the response:
```shell
curl http://localhost:8080/greet

```

The system variable app.greeting took precedence over the environment property and the value in application.yaml.
```json
{
"message": "HelloFromSystemProperty World!"
}
```

## Custom Configuration Sources
### Full List of Configuration Sources
Here is the full list of external config sources that you can use programmatically.
```
    Environment variables - the property is a name/value pair.
    Java system properties - the property is a name/value pair.
    Resources in the classpath - the contents of the resource is parsed according to its inferred format.
    File - the contents of the file is parsed according to its inferred format.
    Directory - each non-directory file in the directory becomes a config entry: the file name is the key. and the contents of that file are used as the corresponding config String value.
    A URL resource - contents is parsed according to its inferred format. 
```

### Classpath Sources
Add a resource file, named config.properties to the resources directory with the following contents:
```properties
app.greeting=HelloFrom-config.properties
```

Update the Main class, Replace the Config.create() call with buildConfig(), and add buildConfig method:
```java
private static Config buildConfig() {
    return Config.builder()
            .disableEnvironmentVariablesSource()
            .sources(
            classpath("config.properties"),
            classpath("application.yaml"))
            .build();
}
```

Build and run the application (without the system property). Invoke the endpoint:
```shell
curl http://localhost:8080/greet

```

JSON response:
```json
{
"message": "HelloFrom-config.properties World!"
}
```

Update the Main class and replace the buildConfig method:
```java
return Config.builder()
.disableEnvironmentVariablesSource()
.sources(
classpath("application.yaml"),
classpath("config.properties"))
.build();
```

Build and run the application, then invoke the endpoint:
```shell
curl http://localhost:8080/greet

```

JSON response:
```json
{
"message": "HelloFrom-application.yaml World!"
}
```

### External File Sources
Create a file named config-file.properties in the helidon-quickstart-se directory with the following contents:
```properties
app.greeting=HelloFrom-config-file.properties
```

Update the Main class and replace the buildConfig method:
```java
return Config.builder()
.sources(
file("config-file.properties"),
classpath("application.yaml"))
.build();
```

Build and run the application, then invoke the endpoint:
```shell
curl http://localhost:8080/greet

```

JSON response:
```json
{
"message": "HelloFrom-config-file.properties World!"
}
```

We need to add the optional() method, if the file is missing.
```java
file("missing-file").optional()
```

## Directory Source
A directory source treats every file in the directory as a key, and the file contents as the value.

Create a new directory helidon/conf then create a file named app.greeting in that directory with the following contents:
```
HelloFromFileInDirectoryConf
```

Update the Main class and replace the buildConfig method:
```java
return Config.builder()
.sources(
directory("conf"),
classpath("config.properties").optional(),
classpath("application.yaml"))
.build();
```

Build and run the application, then invoke the endpoint and check the response:
```shell
curl http://localhost:8080/greet

```

JSON response:
```json
{
"message": "HelloFromFileInDirectoryConf World!"
}
```

## Exceeding Three Sources
Update the Main class and replace the buildConfig method:
```java
return Config.builder()
.addSource(directory("conf"))  
.addSource(file("config-file.properties"))
.addSource(classpath("config.properties").optional())
.addSource(classpath("application.yaml"))
.build();
```

Build and run the application, then invoke the endpoint:
```shell
curl http://localhost:8080/greet

```

JSON response:
```json
{
"message": "HelloFromFileInDirectoryConf World!"
}
```

## Configuration Profiles
Simplest way to use a profile is to define a config-profile.yaml (and possible other files, such as config-profile-dev.yaml for dev profile) on classpath or on file system, and create config using Config.create(). The profile can be changed by a system property config.profile, or using an environment variable HELIDON_CONFIG_PROFILE.

Create a file named config-profile.yaml in the helidon-quickstart-se directory with the following contents:
```yaml
sources:
    - type: "classpath"
      properties:
        resource: "application.yaml" 
```

Update the Main class and replace the buildConfig method:
```java
return Config.create();

```

Will use config-profile.yaml by default
Build and run the application, then invoke the endpoint:
```shell
curl http://localhost:8080/greet
```

JSON response:
```json
{
"message": "HelloFrom-application.yaml World!"
}
```
The application.yaml resource file was used to get the greeting.

The source precedence order in a profile file is the order of appearance in the file. This is demonstrated below where the config-file.properties has the highest order of precedence.
Replace the contents of the config-profile.yaml file:
```yaml
sources:
    - type: "file"
      properties:
        path: "./config-file.properties"
    - type: "classpath"
      properties:
        resource: "application.yaml"
    - type: "file"
      properties:
        path: "optional-config-file"
        optional: true
```

Restart the application, then invoke the endpoint:
```shell
curl http://localhost:8080/greet

```

JSON response:
```json
{
"message": "HelloFrom-config-file.properties World!"
}
```
The config-file.properties source now takes precedence.

When using a profile file, you need to explicitly include both environment variables and system properties as a source if you want to use them.
Replace the contents of the config-profile.yaml file:
```yaml
sources:
    - type: "environment-variables"
    - type: "system-properties"
    - type: "classpath"
      properties:
        resource: "application.yaml"
    - type: "file"
      properties:
        path: "./config-file.properties"
```

## Accessing Config within an Application

### Accessing Config Using Keys or Navigation
View the GreetService constructor:
```java
greeting.set(Config.global().get("app.greeting").asString().orElse("Ciao"));
```

You can also access the same greeting by navigating the nodes.
Replace the GreetService constructor with the following code:
```java
greeting.set(Config.global().get("app").get("greeting").asString().orElse("Ciao")); 
```

## Using Filters and Collections

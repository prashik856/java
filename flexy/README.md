# FLEXY PROJECT

Used as a backend serve information about files present locally in the file system.

## Configure
in resources/application.yaml
```yaml
local:
  directory: "/media/prashik/Expansion/Windows/Backup"
```
`local.directory` points to the directory where all the files are present locally.
Server will not start if this directory is not found.

## Build Project:
Build the project jar file using the commands below
```shell
# From project root directory
./gradlew clean build assemble

# Add --stacktrace and --info for detailed output
./gradlew clean build assemble --stacktrace --info
```

Run the jar
```shell
# From project root directory
java -jar ./build/libs/flexy.jar
```

## Future Work
- create stars api
- create series api
- create movies api
- Create metadata files and load data on server startup.
- Add metrics for each file and store them in metadata file.
- Add metrics to response of server object.


# Health Check

Helidon has a set of built-in health checks:
```
    deadlock detection

    available disk space

    available heap memory
```

Generated dependencies related to health
```xml
<dependencies>
    <dependency>
        <groupId>io.helidon.webserver.observe</groupId>
        <artifactId>helidon-webserver-observe-health</artifactId>
    </dependency>
    <dependency>
        <groupId>io.helidon.health</groupId>
        <artifactId>helidon-health-checks</artifactId>
    </dependency>
</dependencies>
```

Access the health endpoint
```shell
curl -v http://localhost:8080/observe/health
```

The verbose curl output reports the HTTP status:
```text
< HTTP/1.1 204 No Content
```
The successful status means all health checks reported UP.

To see the details about each health check, add the following features configuration fragment in the server section of the application.yaml. Make sure the features key is at the same level as port and host that are already in the file.

Configuration fragment to include details in the health output (nested under server)
```yaml
server:
  port: 8080
  host: 0.0.0.0
  features:
    observe:
      observers:
        health:
          details: true
```


Restart the process and check the endpoint again
Access the health endpoint
```shell
curl -v http://localhost:8080/observe/health
```

Health check details
```json
{
"status": "UP",
"checks": [
{
"name": "diskSpace",
"status": "UP",
"data": {
"total": "465.63 GB",
"percentFree": "14.10%",
"totalBytes": 499963174912,
"free": "65.67 GB",
"freeBytes": 70513274880
}
},
{
"name": "heapMemory",
"status": "UP",
"data": {
"total": "516.00 MB",
"percentFree": "99.82%",
"max": "8.00 GB",
"totalBytes": 541065216,
"maxBytes": 8589934592,
"free": "500.87 MB",
"freeBytes": 525201320
}
},
{
"name": "deadlock",
"status": "UP"
}
]
}
```

## Adding Custom Health Checks
Create an explicit instance of ObserveFeature which contains a custom HealthObserver with the custom check.

Add that ObserveFeature instance to the WebServerConfig.Builder as a feature.

```java
void snippet1(Config config) {
    AtomicLong serverStartTime = new AtomicLong();  

    HealthObserver healthObserver = HealthObserver.builder() 
            .details(true) 
            .addCheck(() -> HealthCheckResponse.builder() 
                              .status(System.currentTimeMillis() - serverStartTime.get() >= 8000)
                              .detail("time", System.currentTimeMillis())
                              .build(),
                      HealthCheckType.STARTUP,
                      "warmedUp")
            .build();

    ObserveFeature observe = ObserveFeature.builder()
            .config(config.get("server.features.observe")) 
            .addObserver(healthObserver) 
            .build();

    WebServer server = WebServer.builder()
            .config(config.get("server"))
            .addFeature(observe)            
            .routing(Main::routing)
            .build()
            .start();

    serverStartTime.set(System.currentTimeMillis()); 
```

If you access the health endpoint before the server has been up for eight seconds, curl reports the response status as 503 Service Unavailable and displays output similar to the following:
Health response shortly after server restart (partial)
```json
{
"status": "DOWN",
"checks": [
{
"name": "warmedUp",
"status": "DOWN",
"data": {
"time": 1702068978353
}
}
]
}
```

## Accessing Specific Health Check Types
You can choose which category of health check to retrieve when you access the health endpoint by adding the health check type as an additional part of the resource path:
```

    liveness only - http://localhost:8080/observe/health/live

    readiness only - http://localhost:8080/observe/health/ready

    startup only - http://localhost:8080/observe/health/started

    all - http://localhost:8080/observe/health

```

Get only start-up health checks
```shell
curl http://localhost:8080/observe/started

```

JSON response:
```json
{
"status": "UP",
"checks": [
{
"name": "warmedUp",
"status": "UP",
"data": {
"time": 1702069835172
}
}
]
}
```

## Applying Configuration to a Custom Health Observer: Customizing the URL path

### Customizing the endpoint path in the code
Customize the URL path for health checks by invoking the endpoint method on the HealthObserver.Builder.
Set a custom endpoint path
```java
HealthObserver healthObserver = HealthObserver.builder()
.endpoint("/myhealth")
.build();
```

Build and run the application, then verify that the health check endpoint responds at /myhealth:
```shell
curl http://localhost:8080/myhealth
```

Earlier you added health config to the application.yaml config file to turn on detailed output. If you want to run an experiment, change that details setting in the config file to false and stop, rebuild, and rerun the application. Now access the health endpoint (at /myhealth, remember). The output remains detailed because your code—​which has full responsibility for determining the custom health observer’s behavior—​does not apply configuration to the custom observer’s builder.

## Adding configuration to a custom observer
Apply health configuration to your custom health observer
```java
HealthObserver healthObserver = HealthObserver.builder()
.config(config.get("server.features.observe.observers.health"))
.build();
```

## Using Liveness, Readiness, and Startup Health Checks with Kubernetes
Add a readyTime variable to the Main class:
```java
private static AtomicLong readyTime = new AtomicLong(0);
```


content_copy Copied
Change the HealthObserver builder in the Main#main method to use new built-in liveness checks and custom liveness, readiness, and startup checks:
```java
ObserveFeature observe = ObserveFeature.builder()
.config(config.get("server.features.observe"))
.addObserver(HealthObserver.builder()
.useSystemServices(true)
.addCheck(() -> HealthCheckResponse.builder()
.status(readyTime.get() != 0)
.detail("time", readyTime.get())
.build(), HealthCheckType.READINESS)
.addCheck(() -> HealthCheckResponse.builder()
.status(readyTime.get() != 0
&& Duration.ofMillis(System.currentTimeMillis()
- readyTime.get())
.getSeconds() >= 3)
.detail("time", readyTime.get())
.build(), HealthCheckType.STARTUP)
.addCheck(() -> HealthCheckResponse.builder()
.status(HealthCheckResponse.Status.UP)
.detail("time", System.currentTimeMillis())
.build(), HealthCheckType.LIVENESS)
.build())
.build();
```



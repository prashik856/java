# Metrics

Helidon provides three built-in scopes of metrics: base, vendor, and application. Here are the metric endpoints:
```
    /observe/metrics?scope=base - Base meters
    /observe/metrics?scope=vendor - Helidon-specific meters
    /observe/metrics?scope=application - Application-specific metrics data.
```

Metrics dependencies in the generated pom.xml:
```xml
<dependencies>
    <dependency>
        <groupId>io.helidon.webserver.observe</groupId>
        <artifactId>helidon-webserver-observe-metrics</artifactId> 
    </dependency>
    <dependency>
        <groupId>io.helidon.metrics</groupId>
        <artifactId>helidon-metrics-system-meters</artifactId>     
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

Verify the metrics endpoint in a new terminal window:
```shell
curl http://localhost:8080/observe/metrics
```

Text response:
```
# TYPE base:classloader_current_loaded_class_count counter
# HELP base:classloader_current_loaded_class_count Displays the number of classes that are currently loaded in the Java virtual machine.
base:classloader_current_loaded_class_count 7511
# TYPE base:classloader_total_loaded_class_count counter
# HELP base:classloader_total_loaded_class_count Displays the total number of classes that have been loaded since the Java virtual machine has started execution.
base:classloader_total_loaded_class_count 7512
```

You can get the same data in JSON format.
Verify the metrics endpoint with an HTTP accept header:
```shell
curl -H "Accept: application/json"  http://localhost:8080/observe/metrics
```

Get the Helidon requests.count meter:
```shell
curl -H "Accept: application/json"  'http://localhost:8080/observe/metrics?scope=vendor&name=requests.count'
```

JSON response:
```json
{
"requests.count": 6
}
```

## Controlling Metrics Behavior

### Disabling Metrics Subsystem Entirely
Configuration properties file disabling metrics
```yaml
server:
  features:
    observe:
      observers:
        metrics:
          enabled: false
```

Make these changes in config-file.properties file
```properties
server.features.observe.observers.metrics.enabled=false
```

A Helidon SE application can disable metrics processing programmatically.

Disable all metrics behavior
```java
ObserveFeature observe = ObserveFeature.builder()   
        .addObserver(MetricsObserver.builder() 
                             .enabled(false) 
                             .build()) 
        .build(); 

WebServer server = WebServer.builder() 
        .config(Config.global().get("server"))
        .addFeature(observe)
        .routing(Main::routing)
        .build()
        .start();
```

These builders and interfaces also have methods which accept Config objects representing the metrics node from the application configuration.

With metrics processing disabled, Helidon never updates any meters and the /observe/metrics endpoints respond with 404.

## Collecting Basic and Extended Key Performance Indicator (KPI) Metrics
Helidon SE also includes additional, extended KPI metrics which are disabled by default:

```
    current number of requests in-flight - a Gauge (requests.inFlight) of requests currently being processed

    long-running requests - a Counter (requests.longRunning) measuring the total number of requests which take at least a given amount of time to complete; configurable, defaults to 10000 milliseconds (10 seconds)

    load - a Counter (requests.load) measuring the number of requests worked on (as opposed to received)

    deferred - a Gauge (requests.deferred) measuring delayed request processing (work on a request was delayed after Helidon received the request)
```


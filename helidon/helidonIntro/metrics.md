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

You can enable and control these meters using configuration:
```yaml
server:
  features:
    observe:
      observers:
        metrics:
          key-performance-indicators:
            extended: true
            long-running:
              threshold-ms: 2000
```

Your Helidon SE application can also control the KPI settings programmatically.

Assign KPI metrics behavior from code
```java
KeyPerformanceIndicatorMetricsConfig kpiConfig =
        KeyPerformanceIndicatorMetricsConfig.builder() 
                .extended(true) 
                .longRunningRequestThreshold(Duration.ofSeconds(4)) 
                .build();

MetricsObserver metrics = MetricsObserver.builder()
        .metricsConfig(MetricsConfig.builder() 
                               .keyPerformanceIndicatorMetricsConfig(kpiConfig)) 
        .build();

ObserveFeature observe = ObserveFeature.builder()
        .config(config.get("server.features.observe"))
        .addObserver(metrics) 
        .build();

WebServer server = WebServer.builder() 
        .config(config.get("server"))
        .addFeature(observe)
        .routing(Main::routing)
        .build()
        .start();
```

## Metrics Metadata
Each meter has associated metadata that includes:
```
    name: The name of the meter.
    units: The unit of the meter such as time (seconds, milliseconds), size (bytes, megabytes), etc.
    a description of the meter. 
```

Get the metrics metadata using HTTP OPTIONS method:
```shell
curl -X OPTIONS -H "Accept: application/json"  'http://localhost:8080/observe/metrics?scope=base'
```

## Counter Meter
The Counter meter is a monotonically increasing number. The following example demonstrates how to use a Counter to track the number of times the /cards endpoint is called.

Create a new class named GreetingCards with the following code:
```java
public class GreetingCards implements HttpService {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Map.of());

    private final Counter cardCounter; 

    GreetingCards() {
        cardCounter = Metrics.globalRegistry()
                .getOrCreate(Counter.builder("cardCount")
                                     .description("Counts card retrievals")); 
    }

    @Override
    public void routing(HttpRules rules) {
        rules.get("/", this::getDefaultMessageHandler);
    }

    private void getDefaultMessageHandler(ServerRequest request, ServerResponse response) {
        cardCounter.increment(); 
        sendResponse(response, "Here are some cards ...");
    }

    private void sendResponse(ServerResponse response, String msg) {
        JsonObject returnObject = JSON.createObjectBuilder().add("message", msg).build();
        response.send(returnObject);
    }
}
```

Update the routing method in the main class as follows:
```java
static void routing(HttpRouting.Builder routing) {
routing
.register("/greet", new GreetService())
.register("/cards", new GreetingCards())
.get("/simple-greet", (req, res) -> res.send("Hello World!"));
}
```

Build and run the application, then invoke the endpoints below:
```shell
curl http://localhost:8080/cards
curl -H "Accept: application/json" 'http://localhost:8080/observe/metrics?scope=application'
```

JSON response:
```json
{
"cardCount": 1
}
```

## Timer Meter
The Timer meter aggregates durations.
In the following example, a Timer meter measures the duration of a method’s execution. Whenever the REST /cards endpoint is called, the code updates the Timer with additional timing information.

Replace the GreetingCards class with the following code:
```java
public class GreetingCards implements HttpService {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Map.of());
    private final Timer cardTimer; 

    GreetingCards() {
        cardTimer = Metrics.globalRegistry()
                .getOrCreate(Timer.builder("cardTimer") 
                                     .description("Times card retrievals"));
    }

    @Override
    public void routing(HttpRules rules) {
        rules.get("/", this::getDefaultMessageHandler);
    }

    private void getDefaultMessageHandler(ServerRequest request, ServerResponse response) {
        Timer.Sample timerSample = Timer.start(); 
        sendResponse(response, "Here are some cards ...");
        response.whenSent(() -> timerSample.stop(cardTimer)); 
    }

    private void sendResponse(ServerResponse response, String msg) {
        JsonObject returnObject = JSON.createObjectBuilder().add("message", msg).build();
        response.send(returnObject);
    }
}
```

Build and run the application, then invoke the endpoints below:
```shell
curl http://localhost:8080/cards
curl http://localhost:8080/cards
curl -H "Accept: application/json"  'http://localhost:8080/observe/metrics?scope=application'
```

JSON response:
```json
{
  "cardTimer": {
    "count": 2,
    "max": 0.01439681,
    "mean": 0.0073397075,
    "elapsedTime": 0.014679415,
    "p0.5": 0.000278528,
    "p0.75": 0.01466368,
    "p0.95": 0.01466368,
    "p0.98": 0.01466368,
    "p0.99": 0.01466368,
    "p0.999": 0.01466368
  }
}
```

## Distribution Summary Meters
The DistributionSummary meter calculates the distribution of a set of values within ranges. This meter does not relate to time at all. The following example records a set of random numbers in a DistributionSummary meter when the /cards endpoint is invoked.

Replace the GreetingCards class with the following code:
```java
public class GreetingCards implements HttpService {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Map.of());
    private final DistributionSummary cardSummary; 

    GreetingCards() {
        cardSummary = Metrics.globalRegistry()
                .getOrCreate(DistributionSummary.builder("cardDist")
                                     .description("random card distribution")); 
    }

    @Override
    public void routing(HttpRules rules) {
        rules.get("/", this::getDefaultMessageHandler);
    }

    private void getDefaultMessageHandler(ServerRequest request, ServerResponse response) {
        Random r = new Random(); 
        for (int i = 0; i < 1000; i++) {
            cardSummary.record(1 + r.nextDouble());
        }
        sendResponse(response, "Here are some cards ...");
    }

    private void sendResponse(ServerResponse response, String msg) {
        JsonObject returnObject = JSON.createObjectBuilder().add("message", msg).build();
        response.send(returnObject);
    }
}
```

Build and run the application, then invoke the endpoints below:
```shell
curl http://localhost:8080/cards
curl -H "Accept: application/json"  'http://localhost:8080/observe/metrics?scope=application'
```

JSON response:
```json
{
  "cardDist": {
    "count": 1000,
    "max": 1.999805150914427,
    "mean": 1.4971440362723523,
    "total": 1497.1440362723522,
    "p0.5": 1.4375,
    "p0.75": 1.6875,
    "p0.95": 1.9375,
    "p0.98": 1.9375,
    "p0.99": 1.9375,
    "p0.999": 1.9375
  }
}
```
The DistributionSummary.Builder allows your code to configure other aspects of the summary, such as bucket boundaries and percentiles to track.

## Gauge Metric
The Gauge meter measures a value that is maintained by code outside the metrics subsystem. As with other meters, the application explicitly registers a gauge. When the /observe/metrics endpoint is invoked, Helidon retrieves the value of each registered Gauge.

Replace the GreetingCards class with the following code:
```java
public class GreetingCards implements HttpService {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Map.of());

    GreetingCards() {
        Random r = new Random();
        Metrics.globalRegistry()
                .getOrCreate(Gauge.builder("temperature",
                                           () -> r.nextDouble(100.0))
                                     .description("Ambient temperature")); 
    }

    @Override
    public void routing(HttpRules rules) {
        rules.get("/", this::getDefaultMessageHandler);
    }

    private void getDefaultMessageHandler(ServerRequest request, ServerResponse response) {
        sendResponse(response, "Here are some cards ...");
    }

    private void sendResponse(ServerResponse response, String msg) {
        JsonObject returnObject = JSON.createObjectBuilder().add("message", msg).build();
        response.send(returnObject);
    }
}
```

Build and run the application, then invoke the endpoint below:
```shell
curl -H "Accept: application/json"  'http://localhost:8080/observe/metrics?scope=application
```

JSON response:
```json
{
"temperature": 46.582132737739066
}
```

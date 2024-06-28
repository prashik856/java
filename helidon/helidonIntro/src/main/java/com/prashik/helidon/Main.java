package com.prashik.helidon;

import io.helidon.config.Config;
import io.helidon.health.HealthCheckType;
import io.helidon.health.HealthCheckResponse;
import io.helidon.logging.common.LogConfig;
import io.helidon.metrics.api.KeyPerformanceIndicatorMetricsConfig;
import io.helidon.metrics.api.MetricsConfig;
import io.helidon.security.providers.oidc.OidcFeature;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import io.helidon.webserver.observe.ObserveFeature;
import io.helidon.webserver.observe.health.HealthObserver;
import io.helidon.webserver.observe.metrics.MetricsObserver;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

import static io.helidon.config.ConfigSources.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public final class Main {
    /**
     * Cannot be instantiated.
     */
    private Main() {
    }

    /**
     * Application main entry point.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        // load logging configuration
        LogConfig.configureRuntime();

        // initialize global config from default configuration

        /*
        // This is normal global config.

        Config config = Config.create();
        Config.global(config);
        */

        // Using properties file as config
        Config config = buildConfigProfile();
        Config.global(config);

        AtomicLong serverStartTime = new AtomicLong();

        HealthObserver healthObserver = HealthObserver.builder()
                .details(true)
                .endpoint("/myhealth")
                .config(config.get("server.features.observe.observers.health"))
                .addCheck(() -> HealthCheckResponse.builder()
                        .status(System.currentTimeMillis() - serverStartTime.get() >= 8000)
                        .detail("time", System.currentTimeMillis())
                        .build(),
                        HealthCheckType.STARTUP,
                        "warmedUp"
                )
                .build();

        AtomicLong readyTime = new AtomicLong(0);
//        ObserveFeature observeFeature = ObserveFeature.builder()
//                .config(config.get("server.feature.observe"))
//                .addObserver(
//                        HealthObserver.builder()
//                                .useSystemServices(true)
//                                .addCheck(() -> HealthCheckResponse.builder()
//                                        .status(readyTime.get() != 0)
//                                        .detail("time", readyTime.get())
//                                        .build(),
//                                        HealthCheckType.READINESS
//                                )
//                                .addCheck(() -> HealthCheckResponse.builder()
//                                        .status(readyTime.get() != 0
//                                                && Duration.ofMillis(System.currentTimeMillis()
//                                                        - readyTime.get())
//                                                .getSeconds() >= 3)
//                                        .detail("time", readyTime.get())
//                                        .build(),
//                                        HealthCheckType.STARTUP
//                                )
//                                .addCheck(() -> HealthCheckResponse.builder()
//                                        .status(HealthCheckResponse.Status.UP)
//                                        .detail("time", System.currentTimeMillis())
//                                        .build(),
//                                        HealthCheckType.LIVENESS
//                                )
//                )
//                .build();

        KeyPerformanceIndicatorMetricsConfig kpiConfig = KeyPerformanceIndicatorMetricsConfig.builder()
                .extended(true)
                .longRunningRequestThreshold(Duration.ofSeconds(4))
                .build();

        MetricsObserver metricsObserver = MetricsObserver.builder()
                .metricsConfig(MetricsConfig.builder()
                        .keyPerformanceIndicatorMetricsConfig(kpiConfig))
                .build();

        ObserveFeature observe = ObserveFeature.builder()
                .config(config.get("server.feature.observe"))
                .addObserver(healthObserver)
//                .addObserver(MetricsObserver.builder()
//                        .enabled(true)
//                        .build())
                .addObserver(metricsObserver)
                .build();

        WebServer server = WebServer.builder()
                .config(config.get("server"))
                .addFeature(observe)
                .routing(Main::routing)
                .build()
                .start();

        System.out.println("WEB server is up! http://localhost:" + server.port() + "/greet");
    }

    /**
     * Config builder
     * */
    private static Config buildConfig() {
        return Config.builder()
                .disableEnvironmentVariablesSource()
                .addSource(directory("conf"))
                .addSource(file("missing-file.properties").optional())
                .addSource(file("config-file.properties"))
                .addSource(classpath("application.yaml"))
                .addSource(classpath("config.properties"))
//                .sources(
//                        directory("conf"),
//                        file("missing-file.properties").optional(),
//                        file("config-file.properties"),
//                        classpath("application.yaml"),
//                        classpath("config.properties")
//                )
                .build();
    }

    private static Config buildConfigProfile() {
        return Config.create();
    }

    /**
     * Updates HTTP Routing and registers observe providers.
     */
    private static void routing(HttpRouting.Builder routing) {
        routing.register("/greet", new GreetService(Config.global()))
                .register("/cards", new GreetingCards())
                .get("/simple-greet", (req, res) -> res.send("Hello World!"));
//                .addFeature(OidcFeature.create(Config.global()));
    }
}
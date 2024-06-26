package com.prashik.helidon;

import io.helidon.config.Config;
import io.helidon.logging.common.LogConfig;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;

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

        WebServer server = WebServer.builder()
                .config(config.get("server"))
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
        routing.register("/greet", new GreetService(Config.global()));
    }
}
package com.prashik.flexy;

import com.prashik.flexy.rest.Stars;
import com.prashik.flexy.utils.Utils;
import io.helidon.config.Config;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import org.apache.logging.log4j.Logger;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * The Main Application class
 *
 * @author prashik
 */
public class Main {
    private static final Logger logger = Utils.getLogger(Main.class.getName());

    /**
     * Private constructor so class cannot be instantiated.
     */
    private Main() {

    }

    /**
     * Application entry point.
     *
     * @param args The input command line arguments
     */
    public static void main(String[] args) throws NoSuchFileException {
        logger.info("Setting up log config");

        logger.info("Reading global config");
        Config config = Config.create();
        Config.global(config);

        Path starsDirectoryPath = Utils.checkDirectory(config.get("local.directory.stars").asString().get(),
                "Stars");

        logger.info("Starting web server");
        WebServer.builder()
                .config(config.get("server"))
                .routing(Main::routing)
                .build()
                .start();
    }

    /**
     * The routing method which maps the API endpoint to corresponding classes.
     *
     * @param routing HTTP routing builder.
     */
    private static void routing(HttpRouting.Builder routing) {
        logger.info("Registering application routes");
        routing.register("/stars", new Stars(Config.global()));
    }
}
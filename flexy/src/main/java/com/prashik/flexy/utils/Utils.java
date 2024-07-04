package com.prashik.flexy.utils;

import io.helidon.config.Config;
import io.helidon.http.NotFoundException;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collections;

/**
 * Helper class which contains common utility functions
 *
 * @author prashik
 */
public class Utils {
    private static final Logger logger = getLogger(Utils.class.getName());

    /**
     * Empty private constructor so that this class cannot be instantiated.
     */
    private Utils() {

    }

    /**
     * Helper method to create logger for a class
     *
     * @param className the name of the class logger belongs to
     * @return the built logger for the class
     */
    public static Logger getLogger(String className) {
        Configurator.setLevel("com.prashik.flexy", Level.DEBUG);
        Configurator.setRootLevel(Level.DEBUG);
        return LogManager.getLogger(className);
    }

    /**
     * Helper method to create JSON Builder
     *
     * @return the empty JSON Builder created
     */
    public static JsonBuilderFactory getJSONBuilder() {
        return Json.createBuilderFactory(Collections.emptyMap());
    }

    /**
     * Function to check if the directory path provided in config path exists or not.
     *
     * @param directory The local directory present in the server
     * @param API The target API of the path provided
     * @return the path provided in the config yaml
     * @throws NoSuchFileException the function throws not found exception if path is not found and exists.
     */
    public static Path checkDirectory(String directory, String API) throws NoSuchFileException {
        logger.info("{} Directory Value: {}", API, directory);

        // Check if directory exists, if not, quit.
        Path directoryPath = Path.of(directory);

        if(!Files.exists(directoryPath)) {
            logger.error("Could not locate {} directory {}. Exiting..",
                    API, directoryPath);
            throw new NoSuchFileException(directoryPath + " not found.");
        }

        return directoryPath;
    }
}

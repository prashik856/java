package com.prashik.flexy.utils;

import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

/**
 * Helper class which contains common utility functions
 *
 * @author prashik
 */
public class Utils {

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
}

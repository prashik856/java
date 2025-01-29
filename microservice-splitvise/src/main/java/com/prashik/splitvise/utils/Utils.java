package com.prashik.splitvise.utils;

import io.helidon.config.Config;
import io.helidon.http.Status;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        Configurator.setLevel("com.prashik.splitvise", Level.DEBUG);
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
     * Function to create Ok Json builder
     *
     * @return returns a JsonObjectBuild with OK reason and status.
     */
    public static JsonObjectBuilder createOkJsonResponse(JsonBuilderFactory JSON) {
        String reason = "OK";
        String status = Integer.toString(Status.OK_200.code());

        JsonObjectBuilder jsonObjectBuilder = JSON.createObjectBuilder();
        jsonObjectBuilder.add("reason", reason);
        jsonObjectBuilder.add("status", status);
        return jsonObjectBuilder;
    }

    /**
     * the method to send back response to the client.
     *
     * @param serverResponse the server response object
     * @param returnObject the build JSON object to be returned.
     */
    public static void sendOKResponse(ServerResponse serverResponse,
                                JsonObject returnObject) {
        serverResponse.status(Status.OK_200);
        serverResponse.send(returnObject);
    }

    public static void printSqlExceptions(SQLException ex) {
        logger.info("SQL Exception: {}", ex.getMessage());
        logger.info("SQL State: {}", ex.getSQLState());
        logger.info("Vendor Error: {}", ex.getErrorCode());
    }

    public static Connection getDatabaseConnection(Config config) {
        String databaseUrl = config.get("database.url").asString().get();
        String databaseUser = config.get("database.user").asString().get();
        String databasePassword = config.get("database.password").asString().get();
        String jdbcUrl = databaseUrl + "?"
                + "user=" + databaseUser
                + "&password=" + databasePassword;
        logger.info("Database Url: {}", databaseUrl);
        logger.info("Database User: {}", databaseUser);
        logger.info("Database Password: {}", databasePassword);

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl);
        } catch(SQLException ex) {
            logger.info("Count not get an instance of JDBC Driver.");
            printSqlExceptions(ex);
            System.exit(1);
        }

        return connection;
    }
}

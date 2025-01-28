package com.prashik.splitvise;

import com.prashik.splitvise.model.AllCustomers;
import com.prashik.splitvise.model.AllGroups;
import com.prashik.splitvise.model.AllTransactions;
import com.prashik.splitvise.rest.Details;
import com.prashik.splitvise.rest.Persons;
import com.prashik.splitvise.utils.Utils;
import io.helidon.config.Config;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Main Application class
 *
 * @author prashik
 */
public class Main {
    private static final Logger logger = Utils.getLogger(Main.class.getName());
    private AllCustomers allCustomers;
    private AllGroups allGroups;
    private AllTransactions allTransactions;

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
    public static void main(String[] args) {
        logger.info("Setting up log config");

        logger.info("Reading global config");
        Config config = Config.create();
        Config.global(config);

        String databaseUrl = config.get("database.url").asString().get();
        String databaseUser = config.get("database.user").asString().get();
        String databasePassword = config.get("database.password").asString().get();
        String jdbcUrl = databaseUrl + "?"
                + "user=" + databaseUser
                + "&password=" + databasePassword;
        logger.info("Database Url: {}", databaseUrl);
        logger.info("Database User: {}", databaseUser);
        logger.info("Database Password: {}", databasePassword);

        logger.info("Reading database to collect stored data.");
        // calls to database to initialize allCustomers, allGroups and allTransactions
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcUrl);
        } catch (SQLException ex) {
            logger.info("Count not get an instance of JDBC Driver.");
            logger.info("SQL Exception: {}", ex.getMessage());
            logger.info("SQL State: {}", ex.getSQLState());
            logger.info("Vendor Error: {}", ex.getErrorCode());
        }

        logger.info("Starting web server");
        System.exit(0);
        try {
            WebServer.builder()
                    .config(config.get("server"))
                    .routing(Main::routing)
                    .build()
                    .start();
        } catch (Exception e) {
            logger.error("Error starting webserver.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * The routing method which maps the API endpoint to corresponding classes.
     *
     * @param routing HTTP routing builder.
     */
    private static void routing(HttpRouting.Builder routing) {
        logger.info("Registering application routes");
        routing.register("/api/v1/details", new Details(Config.global()))
                .register("/api/v1/person", new Persons(Config.global()));
    }
}
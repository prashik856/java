package com.prashik.splitvise;

import com.prashik.splitvise.dao.GroupsDAO;
import com.prashik.splitvise.dao.PersonsDAO;
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

import java.sql.*;

/**
 * The Main Application class
 *
 * @author prashik
 */
public class Main {
    private static final Logger logger = Utils.getLogger(Main.class.getName());
    private static AllCustomers allCustomers = null;
    private static AllGroups allGroups = null;
    private static AllTransactions allTransactions = null;

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

        logger.info("Getting database connection");
        Connection connection = Utils.getDatabaseConnection(config);

        logger.info("Reading database to collect all persons.");
        allCustomers = new PersonsDAO().getAllCustomers(connection);

        logger.info("Reading database to collect all groups.");
        allGroups = new GroupsDAO().getAllGroups(connection);

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
                .register("/api/v1/person", new Persons(Config.global(), allCustomers));
    }
}
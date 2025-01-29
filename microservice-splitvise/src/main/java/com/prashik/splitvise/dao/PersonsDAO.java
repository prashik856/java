package com.prashik.splitvise.dao;

import com.prashik.splitvise.model.AllCustomers;
import com.prashik.splitvise.utils.Utils;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.prashik.splitvise.utils.Utils.getLogger;

public class PersonsDAO {
    private static final Logger logger = getLogger(PersonsDAO.class.getName());
    private Statement statement = null;
    private ResultSet resultSet = null;
    private final String ALL_PERSONS_QUERY = "SELECT Id, PhoneNumber, EmailId, Name, GroupSet, "
            + "CreationTime FROM Persons";

    public PersonsDAO() {

    }

    private ResultSet executeQuery(Connection connection,
                                   String query) {
        try {
            this.statement = connection.createStatement();
            this.resultSet = this.statement.executeQuery(query);
        } catch (SQLException ex) {
            logger.info("Could not execute query : {}", query);
            Utils.printSqlExceptions(ex);
        }
        return this.resultSet;
    }

    private void cleanupResults() {
        if(this.resultSet != null) {
            try {
                this.resultSet.close();
            } catch (SQLException ex) {
                logger.info("Exception happened when closing resultSet.");
                Utils.printSqlExceptions(ex);
            }
            this.resultSet = null;
        }

        if (this.statement != null) {
            try {
                this.statement.close();
            } catch (SQLException ex) {
                logger.info("Exception happened when closing statement.");
                Utils.printSqlExceptions(ex);
            }
            this.statement = null;
        }
    }

    public void getAllCustomers(Connection connection) {
        this.resultSet = executeQuery(connection, ALL_PERSONS_QUERY);
        cleanupResults();
    }
}

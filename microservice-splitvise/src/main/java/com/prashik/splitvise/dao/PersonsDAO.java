package com.prashik.splitvise.dao;

import com.prashik.splitvise.model.AllCustomers;
import com.prashik.splitvise.model.Person;
import com.prashik.splitvise.utils.Utils;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.sql.*;

public class PersonsDAO {
    private static final Logger logger = Utils.getLogger(PersonsDAO.class.getName());
    private Statement statement = null;
    private ResultSet resultSet = null;
    private final String ALL_PERSONS_QUERY = "SELECT Id, PhoneNumber, EmailId, Name, GroupSet, "
            + "CreationTime FROM Persons";
    private final AllCustomers allCustomers = new AllCustomers();

    public PersonsDAO() {

    }

    public AllCustomers getAllCustomers(Connection connection) {
        this.resultSet = Utils.executeQuery(connection, this.statement, this.resultSet, ALL_PERSONS_QUERY);

        try {
            while (this.resultSet.next()) {
                int id = this.resultSet.getInt("Id");
                BigInteger phoneNumber = this.resultSet.getBigDecimal("PhoneNumber").toBigInteger();
                String emailId = this.resultSet.getString("EmailId");
                String name = this.resultSet.getString("Name");
                String groupSet = this.resultSet.getString("GroupSet");
                Timestamp timestamp = this.resultSet.getTimestamp("CreationTime");

                Person person = new Person();
                person.setId(id);
                person.setEmailId(emailId);
                person.setName(name);
                person.setCreationTime(timestamp);
                person.setGroupSet(Utils.getGroupSet(groupSet));

                logger.info("Person Id: {} ", id);
                logger.info("Person Phone Number: {} ", phoneNumber);
                logger.info("Person Email: {} ", emailId);
                logger.info("Person Name: {} ", name);
                logger.info("Person Groups: {} ", groupSet);
                logger.info("Creation Time: {}", timestamp);

                allCustomers.addCustomer(person);
            }
        } catch (SQLException ex) {
            logger.info("Caught some exception while getting resultant set.");
            Utils.printSqlExceptions(ex);
        }

        this.statement = Utils.cleanup(this.statement);
        this.resultSet = Utils.cleanup(this.resultSet);

        return allCustomers;
    }
}

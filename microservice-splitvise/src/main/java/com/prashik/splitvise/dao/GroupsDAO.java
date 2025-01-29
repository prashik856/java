package com.prashik.splitvise.dao;

import com.prashik.splitvise.model.AllGroups;
import com.prashik.splitvise.model.Group;
import com.prashik.splitvise.utils.Utils;
import org.apache.logging.log4j.Logger;

import java.sql.*;

import static com.prashik.splitvise.utils.Utils.getLogger;

public class GroupsDAO {
    private static final Logger logger = getLogger(GroupsDAO.class.getName());
    private Statement statement = null;
    private ResultSet resultSet = null;
    private final String ALL_GROUPS_QUERY = "SELECT Id, Name, CreatedByEmail, PeopleInvolved, "
            + "CreationTime FROM SplitGroups";
    private final AllGroups allGroups = new AllGroups();

    public GroupsDAO() {

    }

    public AllGroups getAllGroups(Connection connection) {
        this.resultSet = Utils.executeQuery(connection, this.statement, this.resultSet, ALL_GROUPS_QUERY);

        try {
            while (this.resultSet.next()) {
                int id = this.resultSet.getInt("Id");
                String name = this.resultSet.getString("Name");
                String createdByEmail = this.resultSet.getString("CreatedByEmail");
                String peopleInvolved = this.resultSet.getString("PeopleInvolved");
                Timestamp timestamp = this.resultSet.getTimestamp("CreationTime");

                Group group = new Group();
                group.setId(id);
                group.setName(name);
                group.setCreatedByEmail(createdByEmail);
                group.setCreationTime(timestamp);
                group.setPeopleInvolved(Utils.getGroupSet(peopleInvolved));

                logger.info("Group Id: {}", id);
                logger.info("Group Name: {}", name);
                logger.info("Group Created by email: {}", createdByEmail);
                logger.info("Group Creation time: {}", timestamp);
                logger.info("Group People involved: {}", peopleInvolved);

                allGroups.addGroup(group);
            }
        } catch (SQLException ex) {
            logger.info("Caught some exception while getting resultant set.");
            Utils.printSqlExceptions(ex);
        }

        this.statement = Utils.cleanup(this.statement);
        this.resultSet = Utils.cleanup(this.resultSet);

        return allGroups;
    }
}

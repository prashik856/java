package com.prashik.splitvise.model;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Group {
    private String id;
    // Group name needs to be unique
    private String name;
    private String createdByEmail;
    private Set<String> peopleInvolved;
    private Timestamp creationTime;

    public Group() {

    }

    public Group(Long id, String name, String createdByEmail) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.createdByEmail = createdByEmail;
        this.creationTime = new Timestamp(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedByEmail() {
        return createdByEmail;
    }

    public void setCreatedByEmail(String createdByEmail) {
        this.createdByEmail = createdByEmail;
    }

    public Set<String> getPeopleInvolved() {
        return peopleInvolved;
    }

    public void setPeopleInvolved(Set<String> peopleInvolved) {
        this.peopleInvolved = peopleInvolved;
    }

    public void addPerson(String personEmail) {
        this.peopleInvolved.add(personEmail);
    }

    public static boolean isEqual(Group group1, Group group2) {
        return Objects.equals(group1.getName(), group2.getName());
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
}

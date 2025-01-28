package com.prashik.splitvise.model;

import java.util.Objects;
import java.util.Set;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Class to store Person Object.
 *
 * @author prashik
 */
public class Person {
    // All of these values cannot be null
    private String id;
    private Long phoneNumber;
    private String emailId;
    private String name;
    private Set<String> groupSet;
    private Timestamp creationTime;

    public Person() {

    }

    public Person(Long phoneNumber, String emailId, String name) {
        this.id = UUID.randomUUID().toString();
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.name = name;
        this.creationTime = new Timestamp(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static boolean isEqual(Person person1, Person person2) {
        return Objects.equals(person1.emailId, person2.emailId)
                || Objects.equals(person1.phoneNumber, person2.phoneNumber);
    }

    public Set<String> getGroupSet() {
        return groupSet;
    }

    public void setGroupSet(Set<String> groupSet) {
        this.groupSet = groupSet;
    }

    public void addToGroup(String groupName) {
        this.groupSet.add(groupName);
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }
}

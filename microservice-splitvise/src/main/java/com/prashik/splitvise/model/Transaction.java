package com.prashik.splitvise.model;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Transaction {
    private String id;
    private String personEmail;
    private String groupName;
    private Set<Shares> shares;
    private Long amount;
    private Timestamp transactionTime;

    public Transaction() {

    }

    public Transaction(String personEmail, String groupName, Set<Shares> shares, Long amount) {
        this.id = UUID.randomUUID().toString();
        this.personEmail = personEmail;
        this.groupName = groupName;
        this.shares = shares;
        this.amount = amount;
        this.transactionTime = new Timestamp(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<Shares> getShares() {
        return shares;
    }

    public void setShares(Set<Shares> shares) {
        this.shares = shares;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public static boolean isEqual(Transaction transaction, Transaction transaction1) {
        return Objects.equals(transaction.getId(), transaction1.getId());
    }
}

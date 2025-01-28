package com.prashik.splitvise.model;

public class Shares {
    private String email;
    private Long contribution;

    public Shares() {

    }

    public Shares(String email, Long contribution) {
        this.email = email;
        this.contribution = contribution;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContribution() {
        return contribution;
    }

    public void setContribution(Long contribution) {
        this.contribution = contribution;
    }
}

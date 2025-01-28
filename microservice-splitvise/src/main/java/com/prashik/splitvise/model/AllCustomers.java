package com.prashik.splitvise.model;

import java.util.HashMap;
import java.util.Set;

public class AllCustomers {
    private Set<Person> allCustomers;
    private HashMap<String, Person> emailMapping;

    public AllCustomers() {

    }

    public AllCustomers(Set<Person> allCustomers) {
        this.allCustomers = allCustomers;
        this.emailMapping = new HashMap<>();
        for(Person person: allCustomers) {
            emailMapping.put(person.getEmailId(), person);
        }
    }

    public Set<Person> getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(Set<Person> allCustomers) {
        this.allCustomers = allCustomers;
    }

    private boolean isPresent(Person newPerson) {
        for(Person person: allCustomers) {
            if (Person.isEqual(person, newPerson)) {
                return true;
            }
        }
        return false;
    }

    public boolean addCustomer(Person person) {
        // Add person if not already present
        if(!this.isPresent(person)) {
            this.allCustomers.add(person);
            this.emailMapping.put(person.getEmailId(), person);
            return true;
        }
        return false;
    }

    public Person getPersonFromEmail(String email) {
        return this.emailMapping.get(email);
    }

    public boolean deleteCustomer(Person person) {
        if(this.isPresent(person)) {
            this.allCustomers.removeIf(person1 -> Person.isEqual(person1, person));
            this.emailMapping.remove(person.getEmailId());
            return true;
        }
        return false;
    }
}

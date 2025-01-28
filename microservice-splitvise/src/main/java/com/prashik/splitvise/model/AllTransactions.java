package com.prashik.splitvise.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AllTransactions {
    private Set<Transaction> allTransactions;
    // for individual transactions
    private HashMap<String, Set<Transaction>> emailTransactions;
    // for group transactions
    private HashMap<String, Set<Transaction>> nameTransactions;

    public AllTransactions() {

    }

    private boolean isPresent(Set<Transaction> transactionSet, Transaction transaction) {
        for(Transaction transaction1: transactionSet) {
            if(Transaction.isEqual(transaction, transaction1)) {
                return true;
            }
        }
        return false;
    }

    private void updateEmailTransactionSet(Transaction transaction, boolean add) {
        String personEmail = transaction.getPersonEmail();
        if (add) {
            if(this.emailTransactions.get(personEmail) == null) {
                // create a new set
                Set<Transaction> personTransactionSet = new HashSet<>();
                personTransactionSet.add(transaction);
                this.emailTransactions.put(personEmail, personTransactionSet);
            } else {
                Set<Transaction> personTransactionSet = this.emailTransactions.get(personEmail);
                if(!isPresent(personTransactionSet, transaction)) {
                    personTransactionSet.add(transaction);
                }
                this.emailTransactions.put(personEmail, personTransactionSet);
            }
        } else {
            if(this.emailTransactions.get(personEmail) != null) {
                Set<Transaction> personTransactionSet = this.emailTransactions.get(personEmail);
                // remove transaction
                personTransactionSet.removeIf(
                        transaction1 -> Transaction.isEqual(transaction1, transaction)
                );
                // update mapping
                this.emailTransactions.put(personEmail, personTransactionSet);
            }
        }
    }

    private void updateNameTransactionSet(Transaction transaction, boolean add) {
        String groupName = transaction.getGroupName();

        if(add) {
            if(this.nameTransactions.get(groupName) == null) {
                Set<Transaction> groupTransactionSet = new HashSet<>();
                groupTransactionSet.add(transaction);
                this.nameTransactions.put(groupName, groupTransactionSet);
            } else {
                Set<Transaction> groupTransactionSet = this.nameTransactions.get(groupName);
                if(!isPresent(groupTransactionSet, transaction)) {
                    groupTransactionSet.add(transaction);
                }
                this.nameTransactions.put(groupName, groupTransactionSet);
            }
        } else {
            if(this.nameTransactions.get(groupName) != null) {
                Set<Transaction> groupTransactionSet = this.nameTransactions.get(groupName);
                // Update group transaction set
                groupTransactionSet.removeIf(
                        transaction1 -> Transaction.isEqual(transaction1, transaction)
                );
                // update mapping
                this.nameTransactions.put(groupName, groupTransactionSet);
            }
        }
    }

    public AllTransactions(Set<Transaction> allTransactions) {
        this.allTransactions = allTransactions;
        this.emailTransactions = new HashMap<>();
        this.nameTransactions = new HashMap<>();

        for(Transaction transaction: allTransactions) {
            this.updateEmailTransactionSet(transaction, true);
            this.updateNameTransactionSet(transaction, true);
        }
    }

    public Set<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public void setAllTransactions(Set<Transaction> allTransactions) {
        this.allTransactions = allTransactions;
    }

    public HashMap<String, Set<Transaction>> getEmailTransactions() {
        return emailTransactions;
    }

    public void setEmailTransactions(HashMap<String, Set<Transaction>> emailTransactions) {
        this.emailTransactions = emailTransactions;
    }

    public HashMap<String, Set<Transaction>> getNameTransactions() {
        return nameTransactions;
    }

    public void setNameTransactions(HashMap<String, Set<Transaction>> nameTransactions) {
        this.nameTransactions = nameTransactions;
    }

    public boolean addTransaction(Transaction transaction) {
        // Add to all transactions
        if(!isPresent(this.allTransactions, transaction)) {
            this.allTransactions.add(transaction);

            // update name transaction
            this.updateNameTransactionSet(transaction, true);

            // update email transaction
            this.updateEmailTransactionSet(transaction, true);

            return true;
        }
        return false;
    }

    public boolean deleteTransaction(Transaction transaction) {
        if(isPresent(this.allTransactions, transaction)) {
            // Update all transactions
            this.allTransactions.removeIf(
                    transaction1 -> Transaction.isEqual(transaction1, transaction)
            );

            // Update email transaction
            this.updateEmailTransactionSet(transaction, false);

            // Update name transaction
            this.updateNameTransactionSet(transaction, false);

            return true;
        }

        return false;
    }
}

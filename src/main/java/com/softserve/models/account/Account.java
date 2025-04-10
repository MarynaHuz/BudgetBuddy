package com.softserve.models.account;

import com.softserve.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private String name;
    private final Currency currency;
    private double balance;
    private List<Transaction> transactions;

    public Account(String name, Currency currency, double balance) {
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.softserve.model.account;

import com.softserve.model.Transaction;

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
}

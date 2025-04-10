package com.softserve.models.account;

import com.softserve.models.Transaction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Account {

    private String name;
    private Currency currency;
    private double balance;
    private List<Transaction> transactions;

    public Account(String name, Currency currency, double balance) {
        this.name = name;
        this.currency = currency;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

}

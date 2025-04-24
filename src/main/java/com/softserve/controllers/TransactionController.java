package com.softserve.controllers;

import com.softserve.models.transaction.Transaction;
import com.softserve.services.Service;
import com.softserve.services.TransactionService;

import java.util.List;

public class TransactionController implements Controller<Transaction> {

    private final Service<Transaction> transactionService;

    public TransactionController() {
        transactionService = new TransactionService();
    }

    public TransactionController(Service<Transaction> transactionService) {
        this.transactionService = transactionService;
    }

    //TODO: Implement methods

    @Override
    public void create(List<String> transaction) {

    }

    @Override
    public void findById(String id) {

    }

    @Override
    public void listAll() {

    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public boolean delete(String id) {
        return false;

    }
}

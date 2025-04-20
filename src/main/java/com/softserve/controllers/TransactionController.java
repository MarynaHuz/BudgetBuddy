package com.softserve.controllers;

import com.softserve.models.transaction.Transaction;
import com.softserve.services.Service;
import com.softserve.services.TransactionService;

public class TransactionController implements Controller<Transaction> {

    private final Service transactionService = new TransactionService();

    //TODO: Implement methods

    @Override
    public void create(Transaction entity) {

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
    public void delete(String id) {

    }
}

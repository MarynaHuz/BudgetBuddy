package com.softserve.services;

import com.softserve.dao.Dao;
import com.softserve.dao.impl.JsonTransactionDao;
import com.softserve.models.transaction.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionService implements Service<Transaction> {

    private final Dao<Transaction> dao = new JsonTransactionDao();

    //TODO: implement methods

    @Override
    public void create(Transaction transaction) {

    }

    @Override
    public Optional<Transaction> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Transaction> listAll() {
        return List.of();
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void removeById(int id) {

    }
}

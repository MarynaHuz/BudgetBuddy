package com.softserve.services;

import com.softserve.dao.DAO;
import com.softserve.dao.impl.JsonTransactionDAO;
import com.softserve.models.transaction.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionService implements Service<Transaction> {

    private final DAO<Transaction> dao = new JsonTransactionDAO();

    //TODO: implement methods

    @Override
    public void create(Transaction transaction) {

    }

    @Override
    public Optional<Transaction> findById(String id) {
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
    public void removeById(String id) {

    }
}

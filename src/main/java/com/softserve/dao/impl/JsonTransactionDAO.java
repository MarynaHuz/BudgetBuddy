package com.softserve.dao.impl;

import com.softserve.dao.DAO;
import com.softserve.models.transaction.Transaction;

import java.util.List;
import java.util.Optional;

import static com.softserve.utils.AppConfig.TRANSACTIONS_JSON;

public class JsonTransactionDAO implements DAO<Transaction> {

    private final String filePath = TRANSACTIONS_JSON.getPath();

    //TODO: implement methods

    @Override
    public void save(Transaction entity) {

    }

    @Override
    public Optional<Transaction> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Transaction> getAll() {
        return List.of();
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void delete(Transaction entity) {

    }
}

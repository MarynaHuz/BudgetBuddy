package com.softserve.dao.impl;

import com.softserve.dao.Dao;
import com.softserve.models.transaction.Transaction;

import java.util.List;

import static com.softserve.utils.AppConfig.TRANSACTIONS_JSON;

public class JsonTransactionDao implements Dao<Transaction> {

    private final String filePath = TRANSACTIONS_JSON.getPath();

    //TODO: implement methods

    @Override
    public void save(Transaction entity) {

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

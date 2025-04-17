package com.softserve.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.softserve.dao.DAO;
import com.softserve.models.transaction.Transaction;

import java.util.List;
import java.util.Optional;

public class JsonTransactionDAO implements DAO<Transaction> {

    private static final TypeReference<List<Transaction>> TYPE_REFERENCE = new TypeReference<>() {};

    //TODO: implement methods

    @Override
    public void save(Transaction entity) {

    }

    @Override
    public Optional<Transaction> read(String entity) {
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

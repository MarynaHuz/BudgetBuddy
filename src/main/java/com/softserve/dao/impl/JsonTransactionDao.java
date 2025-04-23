package com.softserve.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.softserve.dao.Dao;
import com.softserve.models.transaction.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.softserve.utils.AppConfig.TRANSACTIONS_JSON;
import static com.softserve.utils.JsonUtil.readFromJson;
import static com.softserve.utils.JsonUtil.writeToJson;

public class JsonTransactionDao implements Dao<Transaction> {

    private final String filePath = TRANSACTIONS_JSON.getPath();

    //TODO: implement methods

    @Override
    public void save(Transaction transaction) throws IOException {
        List<Transaction> transactions = getAll();
        transactions.add(transaction);
        writeToJson(filePath, transactions);
    }

    @Override
    public List<Transaction> getAll() throws IOException {
        return readFromJson(filePath, new TypeReference<List<Transaction>>() {
        }).orElseGet(ArrayList::new);
    }

    @Override
    public void updateById(int transactionId) {

    }

    @Override
    public void deleteById(int transactionId) {

    }
}

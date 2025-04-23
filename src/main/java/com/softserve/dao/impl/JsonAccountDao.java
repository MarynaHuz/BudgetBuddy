package com.softserve.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.softserve.dao.Dao;
import com.softserve.models.account.Account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.softserve.utils.AppConfig.ACCOUNTS_JSON;
import static com.softserve.utils.JsonUtil.readFromJson;
import static com.softserve.utils.JsonUtil.writeToJson;

public class JsonAccountDao implements Dao<Account> {

    private final String filePath = ACCOUNTS_JSON.getPath();

    @Override
    public void save(List<Account> accounts) throws IOException {
        writeToJson(filePath, accounts);
    }

    @Override
    public List<Account> getAll() throws IOException {
        return readFromJson(filePath, new TypeReference<List<Account>>() {
        }).orElseGet(ArrayList::new);
    }

    @Override
    public void updateById(Account account) throws IOException {
        List<Account> accounts = getAll();
    }

    @Override
    public void deleteById(int accountId) throws IOException {
        List<Account> accounts = getAll();
        accounts.removeIf(acc -> acc.getAccountId() == accountId);
        writeToJson(filePath, accounts);
    }
}

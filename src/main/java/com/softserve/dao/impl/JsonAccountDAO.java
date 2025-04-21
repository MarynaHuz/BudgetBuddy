package com.softserve.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.softserve.dao.DAO;
import com.softserve.models.account.Account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.softserve.utils.AppConfig.ACCOUNTS_JSON;
import static com.softserve.utils.JsonUtil.addToJson;
import static com.softserve.utils.JsonUtil.readFromJson;

public class JsonAccountDAO implements DAO<Account> {

    private final String filePath = ACCOUNTS_JSON.getPath();

    @Override
    public void save(Account account) throws IOException {
        addToJson(filePath, account, new TypeReference<>() {
        });
    }

    @Override
    public Optional<Account> read(String accountId) {
        return Optional.empty();
    }

    @Override
    public List<Account> getAll() throws IOException {
        return readFromJson(filePath, new TypeReference<List<Account>>() {
        }).orElseGet(ArrayList::new);
    }

    @Override
    public void update(Account account) {

    }

    @Override
    public void delete(Account account) {

    }
}

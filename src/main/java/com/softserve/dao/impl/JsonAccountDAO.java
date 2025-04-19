package com.softserve.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.softserve.dao.DAO;
import com.softserve.models.account.Account;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.softserve.utils.AppConfig.ACCOUNTS_JSON;
import static com.softserve.utils.JsonUtil.readFromJson;

public class JsonAccountDAO implements DAO<Account> {

    @Override
    public void save(Account account) {

    }

    @Override
    public Optional<Account> read(String accountId) {
        return Optional.empty();
    }

    @Override
    public List<Account> getAll() {
        try {
            return readFromJson(ACCOUNTS_JSON.getPath(), new TypeReference<>() {});
        } catch (NoSuchFileException e){
            System.err.println("Account file not found: " + ACCOUNTS_JSON.getPath());
            return new ArrayList<>();
        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Failed to retrieve all accounts from JSON file", e);
        }
    }

    @Override
    public void update(Account account) {

    }

    @Override
    public void delete(Account account) {

    }
}

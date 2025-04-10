package com.softserve.dao.impl;

import com.softserve.dao.DAO;
import com.softserve.models.account.Account;

import java.util.List;
import java.util.Optional;

public class JsonAccountDAO implements DAO<Account> {


    @Override
    public void save(Account entity) {

    }

    @Override
    public Optional<Account> read(String accountName) {

        return readAll().stream()
                .filter(acc ->
                        acc.getName().equals(accountName))
                .findFirst();
    }

    @Override
    public List<Account> readAll() {
        return List.of();
    }

    @Override
    public void update(Account entity) {

    }

    @Override
    public void delete(Account entity) {

    }
}

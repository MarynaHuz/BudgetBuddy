package com.softserve.dao.impl;

import com.softserve.dao.AccountDAO;
import com.softserve.models.account.Account;

import java.util.List;
import java.util.Optional;

public class JsonAccountDAO implements AccountDAO {
    @Override
    public void saveAccount(Account account) {

    }

    @Override
    public Optional<Account> findByName(String accountName) {

        return readAccounts().stream()
                .filter(acc ->
                        acc.getName().equals(accountName))
                .findFirst();
    }

    @Override
    public boolean existsByName(String accountName) {
        return false;
    }

    @Override
    public List<Account> readAccounts() {
        return List.of();
    }
}

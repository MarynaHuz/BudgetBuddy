package com.softserve.dao.impl;

import com.softserve.dao.AccountDAO;
import com.softserve.models.account.Account;

import java.util.List;

public class JsonAccountDAO implements AccountDAO {
    @Override
    public void saveAccount(Account account) {

    }

    @Override
    public Account findByName(String accountName) {
        return null;
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

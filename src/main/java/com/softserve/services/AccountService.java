package com.softserve.services;

import com.softserve.dao.DAO;
import com.softserve.dao.impl.JsonAccountDAO;
import com.softserve.models.account.Account;

import java.util.List;
import java.util.Optional;

public class AccountService {

    private final DAO<Account> dao = new JsonAccountDAO();

    public void addAccount(Account account) {
        dao.save(account);
    }

    public Optional<Account> findAccountByName(String name) {
        return dao.read(name);
    }

    public List<Account> findAllAccounts() {
        return dao.readAll();
    }

    public void updateAccount(Account account) {
        dao.update(account);
    }

    public void removeAccount(Account account) {
        dao.delete(account);
    }
}

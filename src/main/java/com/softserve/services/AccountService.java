package com.softserve.services;

import com.softserve.dao.DAO;
import com.softserve.dao.impl.JsonAccountDAO;
import com.softserve.models.account.Account;

import java.util.List;
import java.util.Optional;

public class AccountService implements Service<Account>{

    private final DAO<Account> dao = new JsonAccountDAO();

    @Override
    public void create(Account account) {
        dao.save(account);
    }

    @Override
    public Optional<Account> find(String id) {
        return Optional.empty();
    }

    @Override
    public List<Account> listAll() {
        return List.of();
    }

    @Override
    public void update(Account account) {

    }

    @Override
    public void removeById(String id) {

    }
}

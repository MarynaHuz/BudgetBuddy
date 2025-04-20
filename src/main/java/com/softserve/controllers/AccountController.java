package com.softserve.controllers;

import com.softserve.models.account.Account;
import com.softserve.services.AccountService;
import com.softserve.services.Service;

public class AccountController implements Controller<Account> {

    private final Service accountService = new AccountService();

    //TODO: Implement methods

    @Override
    public void create(Account entity) {

    }

    @Override
    public void findById(String id) {

    }

    @Override
    public void listAll() {

    }

    @Override
    public void update(Account entity) {

    }

    @Override
    public void delete(String id) {

    }
}

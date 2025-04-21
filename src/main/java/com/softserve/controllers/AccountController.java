package com.softserve.controllers;

import com.softserve.models.account.Account;
import com.softserve.services.AccountService;
import com.softserve.services.Service;

import java.io.IOException;

import static com.softserve.validators.IdValidator.validateId;

public class AccountController implements Controller<Account> {

    private final Service<Account> accountService = new AccountService();

    //TODO: Implement methods

    @Override
    public void create(Account entity) {

    }

    @Override
    public void findById(String id) {
        int accountId = validateId(id);
        try {
            accountService.findById(accountId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

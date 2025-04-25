package com.softserve.services;

import com.softserve.dao.Dao;
import com.softserve.dao.impl.JsonAccountDao;
import com.softserve.models.account.Account;
import com.softserve.utils.AppConfig;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.softserve.utils.IdManager.generateNextId;

public class AccountService implements Service<Account> {

    private final Dao<Account> accountDao = new JsonAccountDao();


    //TODO: implement methods

    @Override
    public Account create(Account account) throws IOException {
        List<Account> accounts = accountDao.getAll();
        boolean exists = accounts.stream()
                .anyMatch(existing ->
                        existing.getAccountName().equalsIgnoreCase(account.getAccountName()));

        if (exists) {
            throw new IllegalArgumentException("Account name already exists: " +
                    account.getAccountName());
        }
        int generatedId = generateNextId(AppConfig.ACCOUNT_ID.getPath());
        account.setAccountId(generatedId);
        accounts.add(account);
        accountDao.save(accounts);

        return account;
    }

    @Override
    public Optional<Account> findById(int id) throws IOException {
        List<Account> accounts = listAll();

        return accounts.stream()
                .filter(account -> id == account.getAccountId())
                .findAny();
    }

    @Override
    public List<Account> listAll() throws IOException {
        return accountDao.getAll();
    }

    @Override
    public void update(Account account) {

    }

    @Override
    public Optional<Account> removeById(int accountId) throws IOException {
        return Optional.empty();
    }
}

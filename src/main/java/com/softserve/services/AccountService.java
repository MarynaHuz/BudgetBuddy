package com.softserve.services;

import com.softserve.dao.DAO;
import com.softserve.dao.impl.JsonAccountDAO;
import com.softserve.models.account.Account;
import com.softserve.utils.AppConfig;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.softserve.utils.IdManager.generateNextId;

public class AccountService implements Service<Account> {

    private final DAO<Account> dao = new JsonAccountDAO();

    //TODO: implement methods

    @Override
    public void create(Account account) throws IOException {

        List<Account> accounts = dao.getAll();
        boolean exists = accounts.stream()
                .anyMatch(existing ->
                        existing.getAccountName().equalsIgnoreCase(account.getAccountName()));

        if (exists) {
            throw new IllegalArgumentException("Account name already exists: " +
                    account.getAccountName());
        }
        int generatedId = generateNextId(AppConfig.ACCOUNT_ID.getPath());
        account.setAccountId(generatedId);
        dao.save(account);
    }

    @Override
    public Optional<Account> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Account> listAll() throws IOException {
        return dao.getAll();
    }

    @Override
    public void update(Account account) {

    }

    @Override
    public void removeById(String id) {

    }
}

package com.softserve.services;

import com.softserve.dao.Dao;
import com.softserve.dao.impl.JsonTransactionDao;
import com.softserve.models.account.Account;
import com.softserve.models.transaction.Transaction;
import com.softserve.utils.AppConfig;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.softserve.utils.IdManager.generateNextId;

public class TransactionService implements Service<Transaction> {

    private final Dao<Transaction> dao = new JsonTransactionDao();
    private final Service<Account> accountService = new AccountService();

    //TODO: implement methods

    @Override
    public void create(Transaction transaction) throws IOException {
        int accountId = transaction.getAccountId();

        if (accountService.findById(accountId).isPresent()) {
            transaction.setCurrency(accountService
                    .findById(accountId)
                    .get()
                    .getCurrency());

            transaction.setTransactionId(
                    generateNextId(AppConfig.TRANSACTION_ID.getPath()));
        } else {
            throw new IllegalArgumentException("Account with ID " +
                    transaction.getAccountId() + " does not exist");
        }
        dao.save(transaction);
    }

    @Override
    public Optional<Transaction> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Transaction> listAll() throws IOException {
        return dao.getAll();
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void removeById(int id) {

    }
}

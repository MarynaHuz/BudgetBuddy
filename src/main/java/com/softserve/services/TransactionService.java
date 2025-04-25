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

    private final Dao<Transaction> transactionDao;
    private final Service<Account> accountService;

    public TransactionService() {
        this.transactionDao = new JsonTransactionDao();
        this.accountService = new AccountService();
    }

    public TransactionService(Dao<Transaction> transactionDao, Service<Account> accountService) {
        this.transactionDao = transactionDao;
        this.accountService = accountService;
    }

    // TODO: Add balance calculation and check whether the account has enough balance to add an expense.
    @Override
    public void create(Transaction transaction) throws IOException {
        int accountId = transaction.getAccountId();
        Optional<Account> account = accountService.findById(accountId);

        if (account.isPresent()) {
            transaction.setCurrency(account.get().getCurrency());

            transaction.setTransactionId(
                    generateNextId(AppConfig.TRANSACTION_ID
                            .getPath()));
        } else {
            throw new IllegalArgumentException("Account with ID " +
                    transaction.getAccountId() + " does not exist");
        }
        List<Transaction> transactions = listAll();
        transactions.add(transaction);
        transactionDao.save(transactions);
    }

    @Override
    public Optional<Transaction> findById(int transactionId) throws IOException {
        List<Transaction> transactions = listAll();
        return transactions.stream()
                .filter(transaction -> transactionId == transaction.getTransactionId())
                .findAny();
    }

    @Override
    public List<Transaction> listAll() throws IOException {
        return transactionDao.getAll();
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public Optional<Transaction> removeById(int transactionId) throws IOException {
        List<Transaction> transactions = listAll();

        Optional<Transaction> transactionToRemove = transactions.stream()
                .filter(transaction -> transaction.getTransactionId() == transactionId)
                .findFirst();

        transactionToRemove.ifPresent(transactions::remove);
        transactionDao.save(transactions);
        return transactionToRemove;
    }
}

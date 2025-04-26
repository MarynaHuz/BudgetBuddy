package com.softserve.services;

import com.softserve.dao.Dao;
import com.softserve.dao.impl.JsonTransactionDao;
import com.softserve.models.account.Account;
import com.softserve.models.transaction.Transaction;
import com.softserve.utils.AppConfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.softserve.utils.IdManager.generateNextId;

public class TransactionService implements Service<Transaction> {

    private final Dao<Transaction> transactionDao;
    private final AccountService accountService;

    public TransactionService() {
        this.transactionDao = new JsonTransactionDao();
        this.accountService = new AccountService();
    }

    public TransactionService(Dao<Transaction> transactionDao,
                              AccountService accountService) {
        this.transactionDao = transactionDao;
        this.accountService = accountService;
    }

    @Override
    public Transaction create(Transaction transaction) throws IOException {
        int accountId = transaction.getAccountId();
        Account account = accountService.findById(accountId).get();

        if (transaction.isExpense() &&
                !accountService.hasEnoughBalance(account, transaction.getTransactionAmount())) {
            throw new IllegalStateException("Insufficient balance for this transaction");
        }

        transaction.setCurrency(account.getCurrency());

        transaction.setTransactionId(
                generateNextId(AppConfig.TRANSACTION_ID
                        .getPath()));

        BigDecimal amountChange = transaction.isExpense()
                ? transaction.getTransactionAmount().negate()
                : transaction.getTransactionAmount();

        account.setBalance(account.getBalance().add(amountChange));
        accountService.update(account);
        List<Transaction> transactions = listAll();
        transactions.add(transaction);
        transactionDao.save(transactions);
        return transaction;
    }

    @Override
    public Optional<Transaction> findById(int transactionId) throws IOException {
        List<Transaction> transactions = listAll();
        return transactions.stream()
                .filter(transaction ->
                        transactionId == transaction.getTransactionId())
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

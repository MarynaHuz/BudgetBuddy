package com.softserve.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.softserve.factory.TransactionFactory;
import com.softserve.models.account.Account;
import com.softserve.models.transaction.Transaction;
import com.softserve.services.TransactionService;
import com.softserve.utils.AppConfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.softserve.formatters.TransactionFormatter.formatTransaction;
import static com.softserve.formatters.TransactionFormatter.formatTransactionTable;
import static com.softserve.utils.CategoryManager.getCategoryByName;
import static com.softserve.validators.AmountValidator.validateAmount;
import static com.softserve.validators.DateValidator.validateDate;
import static com.softserve.validators.IdValidator.existsById;
import static com.softserve.validators.IdValidator.validateId;

public class TransactionController implements Controller<String> {

    private final TransactionService transactionService;

    public TransactionController() {
        transactionService = new TransactionService();
    }

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //TODO: Implement methods

    @Override
    public void create(List<String> transactionToAdd) {

        try {
            int accountId = validateId(transactionToAdd.getFirst());
            String transactionCategory = getCategoryByName(
                    transactionToAdd.get(1)).getCategoryName();
            LocalDate transactionDate = validateDate(transactionToAdd.get(2));
            BigDecimal transactionAmount = validateAmount(transactionToAdd.get(3));

            Transaction transaction = TransactionFactory.createTransaction(
                    accountId, transactionCategory, transactionDate, transactionAmount);

            if (existsById(
                    AppConfig.ACCOUNTS_JSON.getPath(),
                    new TypeReference<>() {
                    },
                    Account::getAccountId,
                    accountId)) {
                transactionService.create(transaction);
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException | NullPointerException e) {
            System.err.println("Invalid transaction data: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Error creating transaction: " + e.getMessage());
        }
    }

    @Override
    public void findById(String id) {
        try {
            int transactionId = validateId(id);
            Optional<Transaction> transaction = transactionService.findById(transactionId);
            if (transaction.isPresent()) {
                System.out.println("The transaction has been found:");
                System.out.println(formatTransaction(transaction.get()));
            } else {
                System.out.printf("Transaction with ID %s hasn't been found!", id);
            }
        } catch (IllegalArgumentException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void listAll() {
        try {
            List<Transaction> transactions = transactionService.listAll();
            System.out.println(formatTransactionTable(transactions));
        } catch (IOException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
        }
    }

    /**
     * Handles requests to update a transaction.
     * <p>
     * This method intentionally does not allow updating transactions.
     * Transactions are immutable once created for accounting integrity reasons.
     * Users should instead delete the existing transaction and create a new one
     * if changes are needed.
     *
     * @param transactionToUpdate Map containing transaction ID and parameters that
     *                            would be used for updating (not processed)
     */
    @Override
    public void update(Map<String, List<String>> transactionToUpdate) {
        System.err.println("Transaction updates are not permitted. " +
                "Please delete the transaction and create a new one instead.");
    }

    @Override
    public void delete(String transactionId) {
        try {
            int validId = validateId(transactionId);
            Optional<Transaction> removedTransaction = transactionService.removeById(validId);

            if (removedTransaction.isEmpty()) {
                System.err.println("Transaction with ID " + transactionId + " not found");
                return;
            }
            System.out.println("Removed transaction:");
            System.out.println(formatTransaction(removedTransaction.get()));

        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            System.err.println("Error deleting transaction: " + e.getMessage());
        }
    }
}

package com.softserve.controllers;

import com.softserve.factory.TransactionFactory;
import com.softserve.models.transaction.Transaction;
import com.softserve.services.Service;
import com.softserve.services.TransactionService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.softserve.utils.CategoryManager.getCategoryByName;
import static com.softserve.validators.AmountValidator.validateAmount;
import static com.softserve.validators.DateValidator.validateDate;
import static com.softserve.validators.IdValidator.validateId;

public class TransactionController implements Controller<Transaction> {

    private final Service<Transaction> transactionService;

    public TransactionController() {
        transactionService = new TransactionService();
    }

    public TransactionController(Service<Transaction> transactionService) {
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
            transactionService.create(transaction);
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

    }

    @Override
    public void listAll() {

    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public boolean delete(String id) {
        return false;

    }
}

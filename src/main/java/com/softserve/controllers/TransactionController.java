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

import static com.softserve.formatters.ErrorFormatter.displayError;
import static com.softserve.formatters.TransactionFormatter.formatTransaction;
import static com.softserve.formatters.TransactionFormatter.formatTransactionTable;
import static com.softserve.utils.CategoryManager.getCategoryByName;
import static com.softserve.validators.AmountValidator.validateAmount;
import static com.softserve.validators.DateValidator.validateDate;
import static com.softserve.validators.IdValidator.existsById;
import static com.softserve.validators.IdValidator.validateId;

public class TransactionController implements Controller<String> {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Creates a transaction based on the provided details and saves it to the underlying
     * data storage if the associated account exists. The method also validates the input
     * data, ensuring proper format and valid values, and displays an error message if
     * an exception occurs during processing.
     *
     * @param transactionToAdd List of strings containing transaction details. The list
     *                         must include the following elements in order:
     *                         - Account ID (String representation of an integer)
     *                         - Transaction category (String)
     *                         - Transaction date (String in "dd-MM-yyyy" format)
     *                         - Transaction amount (String representation of a decimal number)
     */
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
                Transaction createdTransaction = transactionService.create(transaction);
                System.out.println(formatTransaction(createdTransaction));
            } else {
                throw new IllegalArgumentException(String.format(
                        "Account with ID %s hasn't been found!", accountId));
            }
        } catch (IllegalArgumentException | IndexOutOfBoundsException |
                 IllegalStateException | NullPointerException e) {
            displayError("Invalid transaction data: " + e.getMessage());
        } catch (IOException e) {
            displayError("Error saving transaction: " + e.getMessage());
        }
    }

    /**
     * Finds and retrieves a transaction by its ID. If the transaction exists, its details
     * are printed to the console in a formatted structure. If the transaction is not found,
     * an {@link IllegalArgumentException} is thrown with a corresponding error message.
     * On validation or processing errors, an error message is displayed to the user.
     *
     * @param id The ID of the transaction to find. It must be a non-empty string
     *           containing a valid integer representation of the transaction ID.
     */
    @Override
    public void findById(String id) {
        try {
            int transactionId = validateId(id);
            Optional<Transaction> transaction = transactionService.findById(transactionId);
            if (transaction.isPresent()) {
                System.out.println("The transaction has been found:");
                System.out.println(formatTransaction(transaction.get()));
            } else {
                throw new IllegalArgumentException(String.format(
                        "Transaction with ID %s hasn't been found!", id));
            }
        } catch (IllegalArgumentException | IOException e) {
            displayError(e.getMessage());
        }
    }

    /**
     * Lists all transactions available in the storage and outputs them in
     * a formatted table. This method retrieves the transactions from the
     * underlying service layer, formats the retrieved data into a human-readable
     * table, and prints the result to the console. If an error occurs during the
     * retrieval process, an error message is displayed.
     * <p>
     * The method performs the following operations:
     * 1. Calls the service layer to fetch all transactions.
     * 2. Formats the transaction data using a defined table structure.
     * 3. Handles and displays any IOExceptions that may occur.
     * <p>
     * Note: This method handles the presentation of data and is primarily used
     * for debugging, logging, or manual inspection.
     */
    @Override
    public void listAll() {
        try {
            List<Transaction> transactions = transactionService.listAll();
            System.out.println(formatTransactionTable(transactions));
        } catch (IOException e) {
            displayError("Error retrieving transactions: " + e.getMessage());
        }
    }

    /**
     * Handles requests to update a transaction.
     * <p>
     * This feature is currently under development and will be available in future releases.
     * For now, transactions cannot be updated directly for accounting integrity reasons.
     * As a workaround, users should delete the existing transaction and create a new one
     * with the desired changes.
     *
     * @param transactionToUpdate Map containing transaction ID and parameters that
     *                            would be used for updating (not processed)
     */
    @Override
    public void update(Map<String, List<String>> transactionToUpdate) {
        displayError("Transaction updates are currently under development. " +
                "For now, please delete the transaction and create a new one with your changes.");
    }

    /**
     * Deletes a transaction identified by its ID. This method performs the following steps:
     * 1. Validates the provided transaction ID.
     * 2. Attempts to remove the transaction associated with the ID from the underlying storage.
     * 3. Displays a success message with the removed transaction details if successful.
     * 4. Displays an error message if the transaction with the specified ID is not found
     * or if any exception occurs during the deletion process.
     *
     * @param transactionId The ID of the transaction to be deleted. It must be a non-empty
     *                      string representing a valid integer value.
     */
    @Override
    public void delete(String transactionId) {
        try {
            int validId = validateId(transactionId);
            Optional<Transaction> removedTransaction = transactionService.removeById(validId);

            if (removedTransaction.isEmpty()) {
                displayError("Transaction with ID " + transactionId + " not found");
                return;
            }
            System.out.println("Transaction successfully removed:");
            System.out.println(formatTransaction(removedTransaction.get()));

        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            displayError("Error deleting transaction: " + e.getMessage());
        }
    }
}

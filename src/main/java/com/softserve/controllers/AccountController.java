package com.softserve.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.softserve.factory.AccountFactory;
import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;
import com.softserve.models.transaction.Transaction;
import com.softserve.services.AccountService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.softserve.formatters.AccountFormatter.*;
import static com.softserve.formatters.ErrorFormatter.displayError;
import static com.softserve.models.account.Currency.parseCurrencyCode;
import static com.softserve.utils.AppConfig.ACCOUNTS_JSON;
import static com.softserve.utils.AppConfig.TRANSACTIONS_JSON;
import static com.softserve.validators.AccountNameValidator.validateAccountName;
import static com.softserve.validators.AmountValidator.validateAmount;
import static com.softserve.validators.BalanceValidator.validateBalance;
import static com.softserve.validators.IdValidator.existsById;
import static com.softserve.validators.IdValidator.validateId;

public class AccountController implements Controller<String> {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Creates a new account using the provided list of parameters.
     * Validates the parameters to ensure the account details are correct.
     * If validation or account creation fails, it displays an appropriate error message.
     *
     * @param accountToCreate a list of strings containing the account details in the following order:
     *                        1. Account Name
     *                        2. Currency Code
     *                        3. Balance
     *                        The list must contain exactly three elements.
     */
    @Override
    public void create(List<String> accountToCreate) {
        try {
            Account newAccount = createAccountFromParameters(accountToCreate);
            Account createdAccount = accountService.create(newAccount);

            System.out.println(formatAccount(createdAccount));

        } catch (IllegalArgumentException e) {
            displayError("Validation error: " + e.getMessage());
        } catch (IOException e) {
            displayError("Error saving account: " + e.getMessage());
        }
    }

    /**
     * Finds an account by its ID and displays the account details
     * if the account exists. If the account is not found, throws
     * an IllegalArgumentException with a relevant error message.
     * Any errors during processing are displayed using the
     * {@code displayError} method.
     *
     * @param id the string representation of the account ID to search for
     *           which will be validated and parsed.
     */
    @Override
    public void findById(String id) {
        try {
            int accountId = validateId(id);
            Optional<Account> account = accountService.findById(accountId);
            if (account.isPresent()) {
                System.out.println("The account has been found:");
                System.out.println(formatAccount(account.get()));
            } else {
                throw new IllegalArgumentException(String.format(
                        "Account with ID %s hasn't been found!", accountId));
            }
        } catch (IllegalArgumentException | IOException e) {
            displayError(e.getMessage());
        }
    }

    /**
     * Lists all accounts and displays them in a formatted table.
     * <p>
     * This method retrieves a list of all accounts from the accountService and
     * formats the data into a readable table structure for display. If an
     * error occurs during the retrieval process, such as an I/O exception, an
     * error message is displayed using the {@code displayError} method.
     * <p>
     * The formatted table includes the following details for each account:
     * - Account ID
     * - Account Name
     * - Currency
     * - Balance
     * <p>
     * Any issues during the retrieval or formatting process are handled gracefully
     * to ensure the method provides meaningful feedback to the user.
     *
     * @throws IOException if an error occurs while accessing the account data
     */
    @Override
    public void listAll() {
        try {
            List<Account> accounts = accountService.listAll();
            System.out.println(formatAccountTable(accounts));
        } catch (IOException e) {
            displayError("Error retrieving accounts: " + e.getMessage());
        }
    }

    /**
     * Updates an existing account using the provided parameters.
     * <p>
     * This method validates the account ID and ensures the account exists before
     * updating it. It also checks if the account has associated transactions and
     * forbids updates to the account balance in such cases to maintain data integrity.
     *
     * @param accountToUpdate a map containing a key-value pair where the key is the
     *                        account ID, and the value is a list of parameters
     *                        required for creating or updating the account.
     *                        The parameters list must include the account name,
     *                        currency, and balance, in that order.
     */
    @Override
    public void update(Map<String, List<String>> accountToUpdate) {
        try {
            List<String> accountParams = accountToUpdate.values()
                    .iterator()
                    .next();
            Account updatedAccount = createAccountFromParameters(accountParams);
            String accountIdStr = accountToUpdate.keySet().iterator().next();
            int accId = validateId(accountIdStr);

            if (!existsById(
                    ACCOUNTS_JSON.getPath(),
                    new TypeReference<>() {
                    },
                    Account::getAccountId,
                    accId)) {
                throw new IllegalArgumentException(String.format(
                        "Account with ID %s hasn't been found!", accId));
            }

            if (existsById(
                    TRANSACTIONS_JSON.getPath(),
                    new TypeReference<>() {
                    },
                    Transaction::getAccountId,
                    accId)) {
                throw new IllegalStateException(
                        "Cannot update the account balance as it has associated transactions.");
            }
            updatedAccount.setAccountId(accId);
            Optional<Account> savedAccount = accountService.update(updatedAccount);

            savedAccount.ifPresent(account ->
                    System.out.println(formatAccount(account)));
        } catch (IllegalArgumentException | IOException | IllegalStateException e) {
            displayError(e.getMessage());
        }
    }

    /**
     * Deletes an account identified by the provided account ID.
     * <p>
     * This method validates the account ID and checks if the account exists in
     * the data source. If the account exists, it is removed and the details of
     * the removed account are printed. If the account does not exist, an
     * IllegalArgumentException is thrown. Additionally, if the account has
     * associated transactions, an IllegalStateException is thrown and the
     * deletion is aborted. Any errors during I/O operations are displayed using
     * the {@code displayError} method.
     *
     * @param accountId the string representation of the account ID to delete,
     *                  which will be validated and parsed before performing the
     *                  deletion.
     */
    @Override
    public void delete(String accountId) {
        try {
            int validatedId = validateId(accountId);
            if (existsById(
                    ACCOUNTS_JSON.getPath(),
                    new TypeReference<>() {
                    },
                    Account::getAccountId,
                    validatedId)) {
                Optional<Account> removedAccount = accountService.removeById(validatedId);

                removedAccount.ifPresent(account -> {
                    System.out.println("Here is the removed account:");
                    System.out.println(formatAccount(account));
                });
            } else {
                throw new IllegalArgumentException(
                        "ID " + accountId + " does not exist in " + ACCOUNTS_JSON.getPath());
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            displayError("Error deleting account: " + e.getMessage());

        } catch (IOException e) {
            displayError(e.getMessage());
        }
    }

    /**
     * Transfers an amount from one account to another, ensuring the transaction parameters
     * are validated and the accounts meet the necessary conditions for the transfer.
     * If any validation or operation fails, appropriate error messages are displayed.
     *
     * @param transferDetails a list of strings containing the transfer details in the following order:
     *                        1. Source Account ID (fromAccountId)
     *                        2. Destination Account ID (toAccountId)
     *                        3. Amount to transfer
     *                        The list must contain exactly three elements.
     * @throws IllegalArgumentException if the transfer details are invalid, such as insufficient arguments
     *                                  or invalid account IDs.
     * @throws IllegalStateException    if the source account has an insufficient balance
     *                                  or if the source and destination accounts are the same.
     * @throws IOException              if an error occurs during the saving of transfer-related data.
     */
    public void transferBetweenAccounts(List<String> transferDetails) {
        try {
            if (transferDetails.size() != 3) {
                throw new IllegalArgumentException("Transfer requires exactly three parameters: " +
                        "fromAccountId, toAccountId, and amount.");
            }
            int fromId = validateId(transferDetails.getFirst());
            int toId = validateId(transferDetails.get(1));
            BigDecimal transferAmount = validateAmount(transferDetails.get(2));

            boolean didTransferSucceed = accountService.transferBetweenAccounts(
                    fromId, toId, transferAmount);

            if (didTransferSucceed) {
                System.out.println(formatTransfer(fromId, toId, transferAmount));
            }

        } catch (IllegalArgumentException | IllegalStateException e) {
            displayError("Error during transfer: " + e.getMessage());
        } catch (IOException e) {
            displayError("Error saving transfer: " + e.getMessage());
        }
    }

    private Account createAccountFromParameters(List<String> accountParameters) {
        if (accountParameters.size() != 3) {
            throw new IllegalArgumentException("Account requires exactly three parameters: " +
                    "accountName, currency, and balance.");
        }
        String accountName = validateAccountName(accountParameters.getFirst());
        Currency currency = parseCurrencyCode(accountParameters.get(1));
        BigDecimal balance = validateBalance(accountParameters.get(2));

        return AccountFactory.createAccount(accountName, currency, balance);
    }
}

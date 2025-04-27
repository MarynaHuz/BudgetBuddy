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

    @Override
    public void create(List<String> accountToCreate) {
        try {
            Account newAccount = createAccountFromParameters(accountToCreate);
            Account createdAccount = accountService.create(newAccount);

            System.out.println(formatAccount(createdAccount));

        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error saving account: " + e.getMessage());
        }
    }

    @Override
    public void findById(String id) {
        try {
            int accountId = validateId(id);
            Optional<Account> account = accountService.findById(accountId);
            if (account.isPresent()) {
                System.out.println("The account has been found:");
                System.out.println(formatAccount(account.get()));
            } else {
                System.out.printf("Account with ID %s hasn't been found!%n", id);
            }
        } catch (IllegalArgumentException | IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void listAll() {
        try {
            List<Account> accounts = accountService.listAll();
            System.out.println(formatAccountTable(accounts));
        } catch (IOException e) {
            System.err.println("Error retrieving accounts: " + e.getMessage());
        }
    }

    /**
     * Updates an existing account using the provided parameters.
     *
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

            if(!existsById(
                    ACCOUNTS_JSON.getPath(),
                    new TypeReference<>() {},
                    Account::getAccountId,
                    accId)){
                throw new IllegalArgumentException("Account with ID %d hasn't been found!" + accId);
            }

            if (existsById(
                    TRANSACTIONS_JSON.getPath(),
                    new TypeReference<>() {},
                    Transaction::getAccountId,
                    accId)) {
                throw new IllegalStateException(
                        "Cannot update the account balance as it has associated transactions.");
            }
            updatedAccount.setAccountId(accId);
            Optional<Account> savedAccount = accountService.update(updatedAccount);

            savedAccount.ifPresent(account ->
                    System.out.println(formatAccount(account)));
        } catch (IllegalArgumentException | IOException | IllegalStateException e){
            System.err.println(e.getMessage());
        }
    }

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
            System.err.println("Error deleting account: " + e.getMessage());

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

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
            System.err.println("Error during transfer: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error saving transfer: " + e.getMessage());
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

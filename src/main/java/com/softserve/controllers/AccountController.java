package com.softserve.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.softserve.factory.AccountFactory;
import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;
import com.softserve.services.AccountService;
import com.softserve.services.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.softserve.formatters.AccountFormatter.formatAccount;
import static com.softserve.formatters.AccountFormatter.formatAccountTable;
import static com.softserve.models.account.Currency.parseCurrencyCode;
import static com.softserve.utils.AppConfig.ACCOUNTS_JSON;
import static com.softserve.validators.AccountNameValidator.validateAccountName;
import static com.softserve.validators.BalanceValidator.validateBalance;
import static com.softserve.validators.IdValidator.existsById;
import static com.softserve.validators.IdValidator.validateId;

public class AccountController implements Controller<Account> {

    private final Service<Account> accountService = new AccountService();

    //TODO: Implement methods

    @Override
    public void create(List<String> accountToCreate) {
        try {
            String accountName = validateAccountName(accountToCreate.getFirst());
            Currency currency = parseCurrencyCode(accountToCreate.get(1));
            BigDecimal balance = validateBalance(accountToCreate.get(2));

            Account account = AccountFactory.createAccount(accountName, currency, balance);

            Account createdAccount = accountService.create(account);
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
                System.out.printf("Account with ID %s hasn't been found!", id);
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

    @Override
    public void update(Account entity) {

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
}

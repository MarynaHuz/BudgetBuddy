package com.softserve.controllers;

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
import static com.softserve.validators.AccountNameValidator.validateAccountName;
import static com.softserve.validators.BalanceValidator.validateBalance;
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
        int accountId = validateId(id);
        try {
            Optional<Account> account = accountService.findById(accountId);
            System.out.printf("The account has been found by id %s: %s%n", id, account);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    public void delete(String id) {
        try {
            int accountId = validateId(id);
            accountService.removeById(accountId);

        } catch (IllegalArgumentException e) {
            System.err.println("Error deleting account: " + e.getMessage());

        } catch (IOException e) {
            //TODO: add descriptive exception
            throw new RuntimeException(e);
        }
    }
}

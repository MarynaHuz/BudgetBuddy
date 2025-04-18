package com.softserve.factory;

import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;

import java.math.BigDecimal;
import java.util.List;

import static com.softserve.utils.IdGeneratorUtil.generateNextId;
import static com.softserve.validators.AccountNameValidator.validateAccountName;
import static com.softserve.validators.BalanceValidator.validateBalance;
import static com.softserve.validators.EnumValidator.validateEnum;

public class AccountFactory {

    private AccountFactory() {
    }

    public static Account createAccount(String accountName, String currency,
                                        String balance, List<Account> accounts) {
        String validatedName = validateAccountName(accountName);
        Currency validatedCurrency = validateEnum(currency, Currency.class);
        BigDecimal validatedBalance = validateBalance(balance);

        int nextId = generateNextId(accounts, Account::getAccountId);

        return Account.builder()
                .accountId(nextId)
                .accountName(validatedName)
                .currency(validatedCurrency)
                .balance(validatedBalance)
                .build();
    }
}

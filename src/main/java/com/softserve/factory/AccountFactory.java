package com.softserve.factory;

import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;

import java.math.BigDecimal;

import static com.softserve.models.account.Currency.parseCurrencyCode;
import static com.softserve.validators.AccountNameValidator.validateAccountName;
import static com.softserve.validators.BalanceValidator.validateBalance;

public class AccountFactory {

    private AccountFactory() {
    }

    public static Account createAccount(String accountName, String currency,
                                        String balance) {
        String validatedName = validateAccountName(accountName);
        Currency validatedCurrency = parseCurrencyCode(currency);
        BigDecimal validatedBalance = validateBalance(balance);

        return Account.builder()
                .accountId(0)
                .accountName(validatedName)
                .currency(validatedCurrency)
                .balance(validatedBalance)
                .build();
    }
}

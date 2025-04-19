package com.softserve.factory;

import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;

import java.math.BigDecimal;

import static com.softserve.validators.AccountNameValidator.validateAccountName;
import static com.softserve.validators.BalanceValidator.validateBalance;
import static com.softserve.validators.EnumValidator.validateEnum;

public class AccountFactory {

    private AccountFactory() {
    }

    public static Account createAccount(String accountName, String currency,
                                        String balance) {
        String validatedName = validateAccountName(accountName);
        Currency validatedCurrency = validateEnum(currency, Currency.class);
        BigDecimal validatedBalance = validateBalance(balance);

        return Account.builder()
                .accountId(0)
                .accountName(validatedName)
                .currency(validatedCurrency)
                .balance(validatedBalance)
                .build();
    }
}

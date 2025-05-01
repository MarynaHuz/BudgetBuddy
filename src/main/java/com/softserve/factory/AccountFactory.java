package com.softserve.factory;

import com.softserve.models.account.Account;
import com.softserve.models.account.Currency;

import java.math.BigDecimal;

public class AccountFactory {

    private AccountFactory() {
    }

    public static Account createAccount(String accountName, Currency currency,
                                        BigDecimal balance) {
        return Account.builder()
                .accountId(0)
                .accountName(accountName)
                .currency(currency)
                .balance(balance)
                .build();
    }
}

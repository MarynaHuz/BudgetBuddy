package com.softserve.formatters;

import com.softserve.models.account.Account;

public class AccountFormatter {

    private AccountFormatter() {
    }

    public static String formatAccount(Account account) {
        return """
                -----------------------------------------
                Account ID: %d
                Account Name: %s
                Currency: %s
                Balance: %.2f
                -----------------------------------------
                """.formatted(
                account.getAccountId(),
                account.getAccountName(),
                account.getCurrency(),
                account.getBalance()
        );
    }
}

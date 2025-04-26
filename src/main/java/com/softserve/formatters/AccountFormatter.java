package com.softserve.formatters;

import com.softserve.models.account.Account;

import java.math.BigDecimal;
import java.util.List;

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

    public static String formatAccountTable(List<Account> accounts) {
        if (accounts == null || accounts.isEmpty()) {
            return "No accounts found.";
        }

        StringBuilder table = new StringBuilder();

        table.append(String.format("%-5s | %-20s | %-8s | %-12s%n",
                "ID", "Account Name", "Currency", "Balance"));

        table.append("-".repeat(55)).append("\n");

        for (Account account : accounts) {
            table.append(String.format("%-5d | %-20s | %-8s | %-12.2f%n",
                    account.getAccountId(),
                    account.getAccountName(),
                    account.getCurrency(),
                    account.getBalance()));
        }

        return table.toString();
    }

    public static String formatTransfer(int fromAccountId, int toAccountId, BigDecimal amount) {
        return """
        -----------------------------------------
        TRANSFER DETAILS
        From Account:  %d
        To Account:    %d
        Amount:       %.2f
        -----------------------------------------
        """.formatted(fromAccountId, toAccountId, amount);
    }
}

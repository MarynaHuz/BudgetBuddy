package com.softserve.utils;

import com.softserve.models.transaction.Transaction;

import java.util.List;

public class TransactionFormatter {

    private TransactionFormatter() {
    }

    public static String formatTransactionTable(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return "No transactions found.";
        }

        StringBuilder table = new StringBuilder();

        table.append(String.format("%-5s | %-5s | %-10s | %-12s | %-18s | %-10s | %-5s%n",
                "ID", "AccID", "Type", "Date", "Category", "Amount", "Currency"));

        table.append("-".repeat(87)).append("%n");

        for (Transaction transaction : transactions) {
            table.append(String.format("%-5d | %-5d | %-10s | %-12s | %-18s | %-10.2f | %-5s%n",
                    transaction.getTransactionId(),
                    transaction.getAccountId(),
                    transaction.getTransactionType(),
                    transaction.getTransactionDate(),
                    transaction.getCategory(),
                    transaction.getTransactionAmount(),
                    transaction.getCurrency()));
        }
        return table.toString();
    }
}

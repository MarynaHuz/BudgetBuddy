package com.softserve.models.validators;

import com.softserve.models.account.Currency;

import java.math.BigDecimal;

public class AccountValidation {

    private static final int MIN_ACCOUNT_NAME_LENGTH = 3;
    private static final BigDecimal MIN_INITIAL_BALANCE = BigDecimal.ZERO;

    private AccountValidation() {
    }

    //TODO: add regex pattern for input String
    public static String validateAccountName(String accountName) {
        if (accountName == null || accountName.trim().length() < MIN_ACCOUNT_NAME_LENGTH) {
            throw new IllegalArgumentException("Account name should be at least "
                    + MIN_ACCOUNT_NAME_LENGTH + " characters.");
        }
        return accountName.trim();
    }

    public static BigDecimal validateInitialBalance(String balance) {
        try {
            BigDecimal parsedBalance = new BigDecimal(balance.trim());
            if (parsedBalance.compareTo(MIN_INITIAL_BALANCE) < 0) {
                throw new IllegalArgumentException("Initial balance cannot be negative.");
            }
            return parsedBalance;
        } catch (NullPointerException | NumberFormatException e) {
            throw new IllegalArgumentException("Initial balance must be a valid non-null number.", e);
        }
    }

    public static Currency validateCurrency(String currency) {
        String formattedCurrency = formatCurrencyString(currency);
        try {
            return Currency.valueOf(formattedCurrency);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency provided: " + currency);
        }
    }

    //TODO: add regex pattern for input String
    private static String formatCurrencyString(String currency) {
        return currency.toUpperCase().trim();
    }

}

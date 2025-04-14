package com.softserve.models.validators;

import com.softserve.models.account.Currency;

import java.math.BigDecimal;

public class AccountValidation {

    private static final int MIN_ACCOUNT_NAME_LENGTH = 3;
    private static final BigDecimal MIN_INITIAL_BALANCE = BigDecimal.ZERO;

    private AccountValidation() {
    }

    public static Currency validateCurrency(String currency) {
        String formattedCurrency = formatCurrencyString(currency);
        try {
            return Currency.valueOf(formattedCurrency);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency provided: " + currency);
        }
    }

    private static String formatCurrencyString(String currency) {
        //TODO: add regex pattern for input String
        return currency.toUpperCase().trim();
    }

}

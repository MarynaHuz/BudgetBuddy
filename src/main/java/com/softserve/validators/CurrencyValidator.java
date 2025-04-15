package com.softserve.validators;

import com.softserve.models.account.Currency;

public class CurrencyValidator {

    private CurrencyValidator() {
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

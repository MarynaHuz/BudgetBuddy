package com.softserve.validators;

import com.softserve.models.account.Currency;

import static com.softserve.utils.Formatter.formatToUpperCase;

public class CurrencyValidator {

    private CurrencyValidator() {
    }

    public static Currency validateCurrency(String currency) {
        assertValidCurrency(currency);
        String formattedCurrency = formatToUpperCase(currency);
        try {
            return Currency.valueOf(formattedCurrency);
        } catch (IllegalArgumentException invalidCurrencyException) {
            throw new IllegalArgumentException("Invalid currency provided: " + currency, invalidCurrencyException);
        }
    }

    private static void assertValidCurrency(String currency) {
        if (!isNonEmptyCurrency(currency)) {
            throw new IllegalArgumentException("Currency must not be null or empty.");
        }
    }

    private static boolean isNonEmptyCurrency(String currency) {
        return currency != null && !currency.trim().isEmpty();
    }
}

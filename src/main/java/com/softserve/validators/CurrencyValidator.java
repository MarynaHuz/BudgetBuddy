package com.softserve.validators;

import com.softserve.models.account.Currency;

public class CurrencyValidator {

    private CurrencyValidator() {
    }

    public static Currency validateCurrency(String currencyCode) {
        validateNonEmptyInput(currencyCode);
        return parseCurrency(currencyCode);
    }

    private static void validateNonEmptyInput(String currencyCode) {
        if (currencyCode == null || currencyCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency code must not be null or empty");
        }
    }

    private static Currency parseCurrency(String currencyCode) {
        try {
            return Currency.valueOf(currencyCode.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw createInvalidCurrencyException(currencyCode);
        }
    }

    private static IllegalArgumentException createInvalidCurrencyException(String currencyCode) {
        return new IllegalArgumentException(
                "Invalid currency code: " + currencyCode + ". " +
                        "Supported currencies are: " + Currency.getSupportedCurrencies()
        );
    }
}

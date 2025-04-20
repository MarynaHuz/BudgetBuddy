package com.softserve.models.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Currency {

    UAH("Ukrainian Hryvnia"),
    EUR("Euro"),
    USD("United States Dollar"),
    GBP("British Pound Sterling"),
    PLN("Polish Zloty");

    private final String fullName;

    public static Currency parse(String currencyCode) {
        validateNonEmptyInput(currencyCode);

        try {
            return valueOf(currencyCode.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw createInvalidCurrencyException(currencyCode);
        }
    }

    public static String getSupportedCurrencies() {
        return String.join(", ", getAllCurrencyCodes());
    }

    public static List<String> getAllCurrencyCodes() {
        return Arrays.stream(values())
                .map(Currency::name)
                .toList();
    }

    private static IllegalArgumentException createInvalidCurrencyException(String currencyCode) {
        return new IllegalArgumentException(
                "Invalid currency code: " + currencyCode + ". " +
                        "Supported currencies are: " + getSupportedCurrencies()
        );
    }

    private static void validateNonEmptyInput(String currencyCode) {
        if (currencyCode == null || currencyCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency code must not be null or empty");
        }
    }
}

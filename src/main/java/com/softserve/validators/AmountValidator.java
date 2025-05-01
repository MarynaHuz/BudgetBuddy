package com.softserve.validators;

import java.math.BigDecimal;

public class AmountValidator {

    static final String NEGATIVE_AMOUNT_ERROR_MESSAGE =
            "Invalid amount: amount must be positive.";
    static final String PARSING_ERROR_MESSAGE =
            "Invalid amount: must be a valid non-null number.";

    private AmountValidator() {
    }

    public static BigDecimal validateAmount(String amount) {
        BigDecimal parsedAmount = parseAmount(amount);
        assertPositiveAmount(parsedAmount);
        return parsedAmount;
    }

    static BigDecimal parseAmount(String amount) {
        try {
            return new BigDecimal(amount.trim());
        } catch (NullPointerException | NumberFormatException e) {
            throw new IllegalArgumentException(PARSING_ERROR_MESSAGE, e);
        }
    }

    public static void assertPositiveAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(NEGATIVE_AMOUNT_ERROR_MESSAGE);
        }
    }
}
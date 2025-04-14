package com.softserve.validators;

import com.softserve.models.category.CategoryType;

import java.math.BigDecimal;

public class AmountValidator {

    private static final String AMOUNT_ERROR_MESSAGE =
            "Invalid amount: income should be positive and expenses should be negative.";
    private static final String PARSING_ERROR_MESSAGE =
            "Invalid amount: must be a valid non-null number.";

    private AmountValidator() {
    }

    public static BigDecimal validateAmount(String amount, CategoryType categoryType) {
        BigDecimal parsedAmount = parseAmount(amount);
        assertAmountMatchesCategoryRules(parsedAmount, categoryType);
        return parsedAmount;
    }

    private static BigDecimal parseAmount(String amount) {
        try {
            return new BigDecimal(amount.trim());
        } catch (NullPointerException | NumberFormatException e) {
            throw new IllegalArgumentException(PARSING_ERROR_MESSAGE, e);
        }
    }

    private static void assertAmountMatchesCategoryRules(BigDecimal amount,
                                                         CategoryType categoryType) {
        if (!CategoryType.isValidAmountForCategory(amount, categoryType)) {
            throw new IllegalArgumentException(AMOUNT_ERROR_MESSAGE);
        }
    }
}
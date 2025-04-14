package com.softserve.validators;

import java.math.BigDecimal;

import static com.softserve.validators.AmountValidator.parseAmount;

public class BalanceValidator {

    private static final String NON_NEGATIVE_BALANCE_ERROR = "Initial balance cannot be negative.";
    private static final BigDecimal MIN_INITIAL_BALANCE = BigDecimal.ZERO;


    private BalanceValidator() {
    }

    public static BigDecimal validateBalance(String balance) {
        BigDecimal parsedBalance = parseAmount(balance);
        assertNonNegativeBalance(parsedBalance);
        return parsedBalance;
    }

    private static void assertNonNegativeBalance(BigDecimal balance) {
        if (balance.compareTo(MIN_INITIAL_BALANCE) < 0) {
            throw new IllegalArgumentException(NON_NEGATIVE_BALANCE_ERROR);
        }
    }
}

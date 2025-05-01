package com.softserve.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BalanceValidatorTest {

    @ParameterizedTest
    @CsvSource({
            "100.25, 100.25",
            "0.00, 0.00",
            "50, 50",
            "999999.99, 999999.99"
    })
    void validateBalance_validPositiveBalance_shouldReturnParsedValue(String inputBalance,
                                                                      String expectedBalance) {
        BigDecimal result = BalanceValidator.validateBalance(inputBalance);
        assertEquals(new BigDecimal(expectedBalance), result);
    }

    @Test
    void validateBalance_negativeBalance_shouldThrowIllegalArgumentException() {
        String negativeBalance = "-10.50";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> BalanceValidator.validateBalance(negativeBalance));
        assertEquals("Balance must be a positive value", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "abc",
            "12a.34",
            "1.2.3",
            "  "
    })
    void validateBalance_invalidFormat_shouldThrowIllegalArgumentException(String invalidBalance) {
        assertThrows(IllegalArgumentException.class,
                () -> BalanceValidator.validateBalance(invalidBalance));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validateBalance_nullOrEmptyInput_shouldThrowIllegalArgumentException(String nullOrEmptyBalance) {
        assertThrows(IllegalArgumentException.class,
                () -> BalanceValidator.validateBalance(nullOrEmptyBalance));
    }
}
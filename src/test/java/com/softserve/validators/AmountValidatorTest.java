package com.softserve.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AmountValidatorTest {

    @DisplayName("Should successfully parse valid numeric amounts")
    @ParameterizedTest
    @CsvSource({
            "150.00",
            "1000.50",
            "0.01",
            "1000",
            "+1000.00",
            "-200.00"
    })
    void parseAmount_shouldReturnBigDecimal_whenValidNumericInput(String validAmount) {
        BigDecimal result = AmountValidator.parseAmount(validAmount);

        assertNotNull(result);
        assertEquals(new BigDecimal(validAmount.trim()), result,
                "Parsed amount should match input exactly");
    }

    @DisplayName("Should throw exception for invalid number formats")
    @ParameterizedTest
    @ValueSource(strings = {
            "abc",
            "12.34.56",
            "+",
            "-",
            "1,000.00"
    })
    void parseAmount_shouldThrowException_whenInvalidNumberFormat(String invalidAmount) {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> AmountValidator.parseAmount(invalidAmount),
                "Invalid number format should throw parsing exception"
        );
        assertEquals(AmountValidator.PARSING_ERROR_MESSAGE, exception.getMessage());
    }

    @DisplayName("Should throw exception for null, empty, or whitespace-only input")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    void parseAmount_shouldThrowException_whenNullEmptyOrWhitespace(String invalidAmount) {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> AmountValidator.parseAmount(invalidAmount),
                "Null, empty, or whitespace-only amount should throw parsing exception"
        );
        assertEquals(AmountValidator.PARSING_ERROR_MESSAGE, exception.getMessage());
    }

    @DisplayName("Should handle different decimal precisions")
    @ParameterizedTest
    @CsvSource({
            "100, 100",
            "100.0, 100.0",
            "100.00, 100.00",
            "100.000, 100.000"
    })
    void parseAmount_shouldMaintainExactDecimalRepresentation(String input, String expected) {
        BigDecimal result = AmountValidator.parseAmount(input);

        assertEquals(new BigDecimal(expected), result,
                "Should maintain exact decimal representation");
    }

    @DisplayName("Should validate positive amounts")
    @ParameterizedTest
    @ValueSource(strings = {
            "150.00",
            "1000.50",
            "0.01"
    })
    void validateAmount_shouldReturnParsedAmount_whenPositiveAmount(String validAmount) {
        BigDecimal result = AmountValidator.validateAmount(validAmount);

        assertNotNull(result);
        assertTrue(result.compareTo(BigDecimal.ZERO) > 0,
                "Amount should be strictly positive");
        assertEquals(new BigDecimal(validAmount), result,
                "Parsed amount should match input exactly");
    }

    @DisplayName("Should reject zero amount")
    @Test
    void validateAmount_shouldThrowException_whenZeroAmount() {
        String zeroAmount = "0.00";

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> AmountValidator.validateAmount(zeroAmount),
                "Zero amount should be rejected"
        );
        assertEquals(AmountValidator.NEGATIVE_AMOUNT_ERROR_MESSAGE, exception.getMessage());
    }

    @DisplayName("Should reject negative amounts")
    @ParameterizedTest
    @ValueSource(strings = {
            "-200.00",
            "-50.75",
            "-0.01"
    })
    void validateAmount_shouldThrowException_whenNegativeAmount(String negativeAmount) {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> AmountValidator.validateAmount(negativeAmount),
                "Negative amount should be rejected"
        );
        assertEquals(AmountValidator.NEGATIVE_AMOUNT_ERROR_MESSAGE, exception.getMessage());
    }

    @DisplayName("Should not throw exception for positive amounts")
    @ParameterizedTest
    @CsvSource({
            "150.00",
            "1000.50",
            "0.01"
    })
    void assertPositiveAmount_shouldNotThrowException_whenPositiveAmount(String validAmount) {
        BigDecimal amount = new BigDecimal(validAmount);

        assertDoesNotThrow(
                () -> AmountValidator.assertPositiveAmount(amount),
                "Should not throw exception for positive amount"
        );
    }

    @DisplayName("Should throw exception for zero amount")
    @Test
    void assertPositiveAmount_shouldThrowException_whenZeroAmount() {
        BigDecimal zeroAmount = BigDecimal.ZERO;

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> AmountValidator.assertPositiveAmount(zeroAmount),
                "Zero amount should be rejected"
        );
        assertEquals(AmountValidator.NEGATIVE_AMOUNT_ERROR_MESSAGE, exception.getMessage());
    }

    @DisplayName("Should throw exception for negative amounts")
    @ParameterizedTest
    @CsvSource({
            "-200.00",
            "-50.75",
            "-0.01"
    })
    void assertPositiveAmount_shouldThrowException_whenNegativeAmount(String negativeAmount) {
        BigDecimal amount = new BigDecimal(negativeAmount);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> AmountValidator.assertPositiveAmount(amount),
                "Negative amount should be rejected"
        );
        assertEquals(AmountValidator.NEGATIVE_AMOUNT_ERROR_MESSAGE, exception.getMessage());
    }

    @DisplayName("Should throw exception for null amount")
    @Test
    void assertPositiveAmount_shouldThrowException_whenNullAmount() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> AmountValidator.assertPositiveAmount(null),
                "Null amount should be rejected"
        );
        assertEquals(AmountValidator.NEGATIVE_AMOUNT_ERROR_MESSAGE, exception.getMessage());
    }
}


package com.softserve.validators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.softserve.validators.AccountNameValidator.isValid;
import static com.softserve.validators.AccountNameValidator.validateAccountName;
import static org.junit.jupiter.api.Assertions.*;

class AccountNameValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"Savings USD", "Privatbank"})
    void validateAccountName_shouldReturnName_whenNameIsValid(String validName) {
        assertEquals(validName, validateAccountName(validName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1InvalidName", "J", "!*&#"})
    void validateAccountName_shouldThrowException_whenNameIsInvalid(String invalidName) {
        assertThrows(IllegalArgumentException.class, () -> validateAccountName(invalidName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Savings EUR", "Monobank"})
    void isValid_shouldReturnTrue_whenNameIsValid(String validName) {
        assertTrue(isValid(validName));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1InvalidName", "J", "!*&#"})
    void isValid_shouldReturnFalse_whenNameIsInvalid(String invalidName) {
        assertFalse(isValid(invalidName));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void isValid_shouldReturnFalse_whenNameIsNullOrBlank(String blankName) {
        assertFalse(isValid(blankName));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void validateAccountName_shouldThrowException_whenNameIsNullOrBlank(String blankName) {
        assertThrows(IllegalArgumentException.class, () -> validateAccountName(blankName));
    }
}
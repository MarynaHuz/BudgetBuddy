package com.softserve.validators;

import org.junit.jupiter.api.Test;

import static com.softserve.validators.AccountNameValidator.isValid;
import static com.softserve.validators.AccountNameValidator.validateAccountName;
import static org.junit.jupiter.api.Assertions.*;

class AccountNameValidatorTest {

    @Test
    void validateAccountName_shouldReturnName_whenNameIsValid() {
        assertEquals("Savings USD", validateAccountName("Savings USD"));
        assertEquals("Privatbank", validateAccountName("Privatbank"));
    }

    @Test
    void validateAccountName_shouldThrowException_whenNameIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> validateAccountName("1InvalidName"));
        assertThrows(IllegalArgumentException.class, () -> validateAccountName("J"));
        assertThrows(IllegalArgumentException.class, () -> validateAccountName("!*&#"));
    }

    @Test
    void isValid_shouldReturnTrue_whenNameIsValid() {
        assertTrue(isValid("Savings USD"));
        assertTrue(isValid("Monobank"));
    }

    @Test
    void isValid_shouldReturnFalse_whenNameIsInvalid() {
        assertFalse(isValid("1InvalidName"));
        assertFalse(isValid("J"));
        assertFalse(isValid("!*&#"));
    }

    @Test
    void isValid_shouldReturnFalse_whenNameIsNullOrBlank() {
        assertFalse(isValid(null));
        assertFalse(isValid(""));
        assertFalse(isValid("  "));
    }

    @Test
    void validateAccountName_shouldThrowException_whenNameIsNullOrBlank() {
        assertThrows(IllegalArgumentException.class, () -> validateAccountName(null));
        assertThrows(IllegalArgumentException.class, () -> validateAccountName(""));
        assertThrows(IllegalArgumentException.class, () -> validateAccountName("  "));
    }
}
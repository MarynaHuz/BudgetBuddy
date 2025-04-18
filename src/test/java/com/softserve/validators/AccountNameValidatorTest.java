package com.softserve.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountNameValidatorTest {

    @Test
    void validateAccountName_ReturnsName_IfNameIsValid() {
        assertEquals("Savings USD", AccountNameValidator.validateAccountName("Savings USD"));
        assertEquals("Privatbank", AccountNameValidator.validateAccountName("Privatbank"));
    }

    @Test
    void validateAccountName_ThrowsException_IfNameIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> AccountNameValidator.validateAccountName("1InvalidName"));
        assertThrows(IllegalArgumentException.class, () -> AccountNameValidator.validateAccountName("J"));
        assertThrows(IllegalArgumentException.class, () -> AccountNameValidator.validateAccountName("!*&#"));
    }

    @Test
    void isValid_ReturnsTrue_IfNameIsValid() {
        assertTrue(AccountNameValidator.isValid("Savings USD"));
        assertTrue(AccountNameValidator.isValid("Monobank"));
    }

    @Test
    void isValid_ReturnsFalse_IfNameIsInvalid() {
        assertFalse(AccountNameValidator.isValid("1InvalidName"));
        assertFalse(AccountNameValidator.isValid("J"));
        assertFalse(AccountNameValidator.isValid("!*&#"));
    }

    @Test
    void isValid_ReturnsFalse_IfNameIsNullOrBlank() {
        assertFalse(AccountNameValidator.isValid(null));
        assertFalse(AccountNameValidator.isValid(""));
        assertFalse(AccountNameValidator.isValid("  "));
    }

    @Test
    void validateAccountName_ThrowsException_IfNameIsNullOrBlank() {
        assertThrows(IllegalArgumentException.class, () -> AccountNameValidator.validateAccountName(null));
        assertThrows(IllegalArgumentException.class, () -> AccountNameValidator.validateAccountName(""));
        assertThrows(IllegalArgumentException.class, () -> AccountNameValidator.validateAccountName("  "));
    }
}
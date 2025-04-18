package com.softserve.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountNameValidatorTest {

    @Test
    void isValid_ReturnsTrue_IfNameIsValid() {
        assertTrue(AccountNameValidator.isValid("John123"));
        assertTrue(AccountNameValidator.isValid("Emily Jane"));
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
}
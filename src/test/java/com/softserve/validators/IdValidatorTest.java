package com.softserve.validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdValidatorTest {

    /**
     * Tests the validateId method, which ensures that a given string can be
     * converted to a valid positive integer ID. It throws an exception if the
     * ID string is null, empty, not numeric, or not positive.
     */

    @Test
    void validateId_shouldReturnParsedId_whenInputIsValid() {
        String validId = "123";

        int result = IdValidator.validateId(validId);

        assertEquals(123, result);
    }

    @Test
    void validateId_shouldThrowException_whenInputIsNull() {
        String nullId = null;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> IdValidator.validateId(nullId)
        );

        assertEquals("ID must be a positive number", exception.getMessage());
    }

    @Test
    void validateId_shouldThrowException_whenInputIsEmpty() {
        String emptyId = "";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> IdValidator.validateId(emptyId)
        );

        assertEquals("ID must be a positive number", exception.getMessage());
    }

    @Test
    void validateId_shouldThrowException_whenInputIsNonNumeric() {
        String nonNumericId = "abc";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> IdValidator.validateId(nonNumericId)
        );

        assertEquals("ID must be a positive number", exception.getMessage());
    }

    @Test
    void validateId_shouldThrowException_whenInputIsNegativeNumber() {
        String negativeId = "-123";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> IdValidator.validateId(negativeId)
        );

        assertEquals("ID must be a positive number", exception.getMessage());
    }

    @Test
    void validateId_shouldThrowException_whenInputIsZero() {
        String zeroId = "0";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> IdValidator.validateId(zeroId)
        );

        assertEquals("ID must be a positive number", exception.getMessage());
    }

    @Test
    void validateId_shouldReturnParsedId_whenInputHasLeadingAndTrailingSpaces() {
        String idWithSpaces = "  456  ";

        int result = IdValidator.validateId(idWithSpaces);

        assertEquals(456, result);
    }
}
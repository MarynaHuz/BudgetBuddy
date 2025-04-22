package com.softserve.validators;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IdValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"123", "456", "789"})
    void validateId_shouldReturnParsedId_whenInputIsValid(String validId) {
        int expectedId = Integer.parseInt(validId);
        int result = IdValidator.validateId(validId);
        assertEquals(expectedId, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"  456  ", "  789  ", " 123 "})
    void validateId_shouldReturnParsedId_whenInputHasLeadingAndTrailingSpaces(String idWithSpaces) {
        int expectedId = Integer.parseInt(idWithSpaces.trim());
        int result = IdValidator.validateId(idWithSpaces);
        assertEquals(expectedId, result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "abc",
            "-123",
            "0",
    })
    void validateId_shouldThrowException_whenInputIsInvalid(String invalidId) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> IdValidator.validateId(invalidId),
                "Invalid ID format should throw exception"
        );

        assertEquals("ID must be a positive number", exception.getMessage());
    }
}
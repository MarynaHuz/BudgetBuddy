package com.softserve.validators;

import java.util.Optional;

public class IdValidator {

    private IdValidator() {
    }

    private static final String PARSING_ERROR_MESSAGE =
            "ID must be a positive number";

    public static int validateId(String id) {
        int parsedId = parseId(id);
        validatePositive(parsedId);
        return parsedId;
    }

    private static int parseId(String id) {
        return Integer.parseInt(
                Optional.ofNullable(id)
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .filter(s -> s.matches("\\d+"))
                        .orElseThrow(() -> new IllegalArgumentException(PARSING_ERROR_MESSAGE))
        );
    }

    private static void validatePositive(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException(PARSING_ERROR_MESSAGE);
        }
    }
}

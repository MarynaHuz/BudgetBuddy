package com.softserve.validators;

public class IdValidator {

    private IdValidator() {
    }

    private static final String PARSING_ERROR_MESSAGE =
            "ID must be a positive number";

    private static int parseId(String id) {
        if (id == null || id.trim().isEmpty() || !isNumber(id.trim())) {
            throw new IllegalArgumentException(PARSING_ERROR_MESSAGE);
        }
        return Integer.parseInt(id.trim());
    }

    private static boolean isPositiveNumber(int id) {
        if (id <= 0) {
            System.err.println(PARSING_ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

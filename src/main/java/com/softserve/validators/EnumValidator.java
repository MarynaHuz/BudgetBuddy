package com.softserve.validators;

import static com.softserve.utils.Formatter.formatToUpperCase;

public class EnumValidator {

    private EnumValidator() {
    }

    public static <E extends Enum<E>> E validateEnum(String value, Class<E> enumClass) {
        assertNonEmptyValue(value, enumClass);
        String formattedValue = formatToUpperCase(value);
        return parseEnum(formattedValue, enumClass);
    }

    private static <E extends Enum<E>> void assertNonEmptyValue(String value, Class<E> enumClass) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("The value for " + enumClass.getSimpleName() + " must not be null or empty.");
        }
    }
    private static <E extends Enum<E>> E parseEnum(String value, Class<E> enumClass) {
        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException invalidEnumException) {
            throw new IllegalArgumentException("Invalid " +
                    enumClass.getSimpleName() + " supplied: " + value, invalidEnumException);
        }
    }
}

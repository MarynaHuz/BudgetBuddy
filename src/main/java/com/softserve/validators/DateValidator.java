package com.softserve.validators;

import java.time.format.DateTimeFormatter;

public class DateValidator {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private static final String NULL_OR_BLANK_DATE_MESSAGE =
            "Invalid date: date cannot be null or blank.";
    private static final String FORMAT_ERROR_MESSAGE =
            "Invalid date format: expected format is " + DATE_FORMAT + ".";

    private DateValidator() {
    }
}

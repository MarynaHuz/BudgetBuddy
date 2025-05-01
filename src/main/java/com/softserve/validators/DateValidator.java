package com.softserve.validators;

import lombok.Setter;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    @Setter
    private static Clock clock = Clock.systemDefaultZone();

    private static final String NULL_OR_BLANK_DATE_MESSAGE =
            "Invalid date: date cannot be null or blank.";
    private static final String FORMAT_ERROR_MESSAGE =
            "Invalid date format: expected format is " + DATE_FORMAT + ".";
    private static final String FUTURE_DATE_ERROR_MESSAGE =
            "Invalid date: date cannot be in the future.";


    private DateValidator() {
    }

    public static LocalDate validateDate(String date) {
        validateDateNotNullOrBlank(date);
        LocalDate parsedDate = parseDate(date.trim());
        return validateNotFutureDate(parsedDate);
    }

    private static LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(FORMAT_ERROR_MESSAGE +
                    " Provided value: " + date, e);
        }
    }

    private static void validateDateNotNullOrBlank(String date) {
        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException(NULL_OR_BLANK_DATE_MESSAGE);
        }
    }

    private static LocalDate validateNotFutureDate(LocalDate date) {
        LocalDate today = LocalDate.now(clock);
        if (date.isAfter(today)) {
            throw new IllegalArgumentException(FUTURE_DATE_ERROR_MESSAGE +
                    " Provided value: " + date);
        }
        return date;
    }
}

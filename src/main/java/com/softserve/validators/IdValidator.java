package com.softserve.validators;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

import static com.softserve.utils.JsonUtil.readFromJson;

public class IdValidator {

    private IdValidator() {
    }

    private static final String PARSING_ERROR_MESSAGE =
            "ID must be a positive number";


    public static <T> int validateId(String filePath, String id,
                                     ToIntFunction<T> idExtractor) throws IOException {
        int parsedId = parseId(id);

        if (isPositiveNumber(parsedId)) {

            List<T> items = new ArrayList<>(
                    readFromJson(filePath, new TypeReference<>() {
                    }));

            try {
                for (var item : items) {

                    Integer itemId = idExtractor.applyAsInt(item);
                    if (itemId.equals(parsedId)) {
                        return parsedId;
                    }
                }
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

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

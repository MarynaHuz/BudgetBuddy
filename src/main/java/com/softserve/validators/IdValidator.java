package com.softserve.validators;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        if (!isPositiveNumber(parsedId)) {
            return 0;
        }

        List<T> items = readFromJson(filePath, new TypeReference<List<T>>() {
        })
                .orElseGet(ArrayList::new);

        for (var item : items) {
            Integer itemId = idExtractor.applyAsInt(item);
            if (itemId.equals(parsedId)) {
                return parsedId;
            }
        }
        return 0;
    }

    private static int parseId(String id) {
        return Integer.parseInt(
                Optional.ofNullable(id)
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .orElseThrow(() -> new IllegalArgumentException(PARSING_ERROR_MESSAGE))
        );
    }

    private static boolean isPositiveNumber(int id) {
        if (id <= 0) {
            System.err.println(PARSING_ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}

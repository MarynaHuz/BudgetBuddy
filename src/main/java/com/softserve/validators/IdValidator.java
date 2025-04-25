package com.softserve.validators;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;

import static com.softserve.utils.JsonUtil.readFromJson;

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

    public static <T> boolean existsById(String filePath, TypeReference<List<T>> typeRef,
                                         ToIntFunction<T> idExtractor,
                                         int idToCheck) throws IOException {

        List<T> data = readFromJson(filePath, typeRef)
                .orElse(List.of());

        boolean exists = data.stream()
                .anyMatch(item -> idExtractor.applyAsInt(item) == idToCheck);

        if (!exists) {
            throw new IllegalArgumentException("ID " + idToCheck + " does not exist in " + filePath);
        }
        return true;
    }
}

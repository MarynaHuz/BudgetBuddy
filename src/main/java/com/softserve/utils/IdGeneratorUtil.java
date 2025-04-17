package com.softserve.utils;

import java.util.List;
import java.util.function.ToIntFunction;

public class IdGeneratorUtil {

    private IdGeneratorUtil() {
    }

    public static <T> int generateNextId(List<T> items, ToIntFunction<T> idExtractor) {
        return items.stream()
                .mapToInt(idExtractor)
                .max()
                .orElse(0) + 1;
    }
}

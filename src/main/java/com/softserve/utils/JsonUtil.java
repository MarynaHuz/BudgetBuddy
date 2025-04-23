package com.softserve.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static com.softserve.utils.FileUtils.ensureFileExists;

public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.findAndRegisterModules();
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonUtil() {
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static <T> Optional<T> readFromJson(String filePath, TypeReference<T> typeRef) throws IOException {
        Path path = ensureFileExists(filePath);

        if (Files.size(path) == 0) {
            return Optional.empty();
        }
        return Optional.of(OBJECT_MAPPER.readValue(path.toFile(), typeRef));
    }

    public static <T> void writeToJson(String filePath, T data) throws IOException {
        Path path = ensureFileExists(filePath);

        Files.write(path, OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                .writeValueAsBytes(data));
    }
}

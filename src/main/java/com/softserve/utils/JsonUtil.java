package com.softserve.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.softserve.utils.FileUtils.ensureFileExists;
import static com.softserve.utils.FileUtils.validateFilePath;

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

        validateFilePath(filePath);
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }
        if (file.length() == 0) {
            return Optional.empty();
        }
        return Optional.of(OBJECT_MAPPER.readValue(file, typeRef));
    }

    public static <T> void addToJson(String filePath, T itemToAdd,
                                     TypeReference<List<T>> typeRef) throws IOException {

        File file = ensureFileExists(filePath);
        List<T> items = readFromJson(filePath, typeRef).orElseGet(ArrayList::new);
        items.add(itemToAdd);
        serializeToJson(file, items);
    }

    public static <T> void writeToJson(String filePath, T data) throws IOException {
        File file = ensureFileExists(filePath);
        serializeToJson(file, data);
    }

    private static <T> void serializeToJson(File file, T data) throws IOException {
        try (var outputStream = new FileOutputStream(file)) {
            OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValue(outputStream, data);
        }
    }

}

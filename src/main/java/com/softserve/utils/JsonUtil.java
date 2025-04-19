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

    public static <T> T readFromJson(String filePath, TypeReference<T> typeRef) throws IOException {
        File file = new File(filePath);

        if (!file.exists() || file.length() == 0) {
            throw new IOException("File not found or empty: " + filePath);
        }
        return OBJECT_MAPPER.readValue(file, typeRef);
    }

    public static <T> void writeToJson(String filePath, T itemToAdd,
                                       TypeReference<List<T>> typeRef) throws IOException {

        File file = ensureFileExists(filePath);
        List<T> items;
        try {
            items = readFromJson(filePath, typeRef);
        } catch (IOException e) {
            items = new ArrayList<>();
        }

        items.add(itemToAdd);

        try (var outputStream = new FileOutputStream(file)) {
            OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValue(outputStream, items);
        }
    }
}

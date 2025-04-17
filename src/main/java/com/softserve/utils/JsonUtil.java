package com.softserve.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

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

    public static <T> T readJson(String filePath, TypeReference<T> typeRef) throws IOException {
        try (var inputStream = JsonUtil.class.getClassLoader()
                .getResourceAsStream(filePath)) {

            if (inputStream == null) {
                throw new IOException("File '" + filePath + "' not found on classpath.");
            }
            return OBJECT_MAPPER.readValue(inputStream, typeRef);
        }
    }

}

package com.softserve.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        try {
            OBJECT_MAPPER = new ObjectMapper();
            OBJECT_MAPPER.findAndRegisterModules();
            OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        } catch (Exception e) {
            throw new ExceptionInInitializerError();
        }
    }

    private JsonUtil() {
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static <T> T readJson(String resourcePath, TypeReference<T> typeRef) throws IOException {
        try (var inputStream = JsonUtil.class.getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new IOException("Resource '" + resourcePath + "' not found on classpath.");
            }
            return OBJECT_MAPPER.readValue(inputStream, typeRef);
        }
    }


}

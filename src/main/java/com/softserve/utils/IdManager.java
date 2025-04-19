package com.softserve.utils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Optional;

public class IdManager {

    private static final TypeReference<Integer> INTEGER_TYPE_REFERENCE = new TypeReference<>() {};

    private IdManager() {
    }

    public static int generateNextId(String filePath) {
        int currentId = getLastId(filePath);
        int nextId = currentId + 1;
        saveId(filePath, nextId);
        return nextId;
    }

    private static void saveId(String filePath, int nextId) {
        try {
            JsonUtil.writeToJson(filePath, nextId);
        } catch (IOException e) {
            throw new RuntimeException("Error saving ID to file: " + filePath, e);
        }
    }

    private static Optional<Integer> getLastId(String filePath) {
        try {
            return Optional.ofNullable(JsonUtil.readFromJson(filePath, INTEGER_TYPE_REFERENCE));
        } catch (IOException e) {
            System.err.println("Error reading last ID from file: " + filePath +
                    ". Returning empty Optional. " + e.getMessage());
            return Optional.empty();
        }
    }

}

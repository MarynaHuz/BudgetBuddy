package com.softserve.utils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Optional;

public class IdManager {

    private IdManager() {
    }

    public static int generateNextId(String filePath) {
        int nextId = getLastId(filePath)
                .map(id -> id + 1)
                .orElse(1);
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
            return JsonUtil.readFromJson(filePath, new TypeReference<>() {
            });
        } catch (IOException e) {
            System.err.println("Error reading last ID from file: " + filePath +
                    ". Returning empty Optional. " + e.getMessage());
            return Optional.empty();
        }
    }
}

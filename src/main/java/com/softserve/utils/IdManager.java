package com.softserve.utils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

import static com.softserve.utils.JsonUtil.readFromJson;

public class IdManager {

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

    private static int getLastId(String filePath) {
        try {
            Integer lastId = readFromJson(filePath, new TypeReference<>() {});
            return lastId != null? lastId: 0;
        } catch (IOException e) {
            return 0;
        }
    }
}

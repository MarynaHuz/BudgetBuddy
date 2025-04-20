package com.softserve.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    private FileUtils() {
    }

    public static void ensureDirectoryExists(String filePath) throws IOException {
        validateFilePath(filePath);
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                throw new IOException("Failed to create directory: " +
                        parentDir.getAbsolutePath());
            }
        }
    }

    public static File createFileIfNotExists(String filePath) throws IOException {
        validateFilePath(filePath);
        File file = new File(filePath);

        if (!file.exists()) {
            boolean created = file.createNewFile();
            if (!created) {
                throw new IOException("Failed to create file: " +
                        file.getAbsolutePath());
            }
        }
        return file;
    }

    public static File ensureFileExists(String filePath) throws IOException {
        validateFilePath(filePath);
        ensureDirectoryExists(filePath);
        return createFileIfNotExists(filePath);
    }

    public static void validateFilePath(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
    }
}

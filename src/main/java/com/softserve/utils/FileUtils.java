package com.softserve.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    private FileUtils() {
    }

    public static Path ensureFileExists(String filePath) throws IOException {
        ensureDirectoryExists(filePath);
        return createFileIfNotExists(filePath);
    }

    public static void ensureDirectoryExists(String filePath) throws IOException {
        validateFilePath(filePath);
        Path path = Paths.get(filePath);
        Path parentDir = path.getParent();

        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
    }

    public static Path createFileIfNotExists(String filePath) throws IOException {
        validateFilePath(filePath);
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        return path;
    }

    public static void validateFilePath(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
    }
}

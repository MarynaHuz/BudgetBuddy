package com.softserve.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    private FileUtils() {
    }

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    public static void ensureDirectoryExists(String filePath) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
            }
        }
    }


}

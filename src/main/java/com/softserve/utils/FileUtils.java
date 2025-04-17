package com.softserve.utils;

import java.io.File;

public class FileUtils {

    private FileUtils() {
    }

    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

}

package com.yanemr.ccwc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static byte[] readFile(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path).toAbsolutePath());
    }
}

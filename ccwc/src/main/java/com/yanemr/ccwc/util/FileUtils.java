package com.yanemr.ccwc.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static byte[] readFile(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path).toAbsolutePath());
    }

    public static byte[] readInput() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[32 * 1024];

        int bytesRead;
        while ((bytesRead = System.in.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }
}

package org.yanemr.ccjq.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class InputUtil {

    public static String readFile(String fileName) throws IOException {
        String currentDirectory = Path.of("").toAbsolutePath().toString();
        Path filePath = Path.of(currentDirectory, fileName);
        System.out.println(filePath);
        return Files.readString(filePath, StandardCharsets.UTF_16);
    }

    public static String readInputStream() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[32 * 1024];

        int bytesRead;
        while ((bytesRead = System.in.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toString(StandardCharsets.UTF_8);
    }
}

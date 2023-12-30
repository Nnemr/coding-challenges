package com.yanemr.ccwc;

import com.yanemr.ccwc.dto.Result;
import com.yanemr.ccwc.output.OutputFormatter;
import com.yanemr.ccwc.output.OutputFormatterFactory;
import com.yanemr.ccwc.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

@Command(name = "ccwc")
public class WordCounter implements Callable<Result> {

    @Option(names = {"-c", "--bytes"}, usageHelp = true)
    boolean countBytes;

    @Option(names = {"-m", "--chars"}, usageHelp = true)
    boolean countChars;

    @Option(names = {"-l", "--lines"}, usageHelp = true)
    boolean countLines;

    @Option(names = {"-w", "--words"}, usageHelp = true)
    boolean countWords;

    @Parameters(index = "0", defaultValue = "", arity = "0..1")
    private String file;

    @Override
    public Result call() throws Exception {

        String currentDirectory = System.getProperty("user.dir");
        byte[] bytes = null;
        if (file.isBlank()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[32 * 1024];

            int bytesRead;
            while ((bytesRead = System.in.read(buffer)) > 0) {
                baos.write(buffer, 0, bytesRead);
            }
            bytes = baos.toByteArray();

        } else {
            bytes = FileUtils.readFile(Paths.get(currentDirectory, file).toString());
        }
        var result = new Result(file);
        if (countBytes) {
            result.numberOfBytes = bytes.length;
        }

        if (countChars) {
            result.numberOfChars = bytes.length;
        }

        if (countLines) {
            result.numberOfLines = 0;
            for (byte b : bytes) {
                if (b == '\n') {
                    result.numberOfLines++;
                }
            }
        }

        if (countWords) {
            result.numberOfWords = 0;
            for (int i = 1; i < bytes.length; ++i) {
                if (Character.isWhitespace(bytes[i])
                        && !Character.isWhitespace(bytes[i - 1])) {
                    ++result.numberOfWords;
                }
            }
        }
        OutputFormatter formatter = OutputFormatterFactory.getInstance();
        System.out.println(formatter.format(result));
        return result;
    }
}

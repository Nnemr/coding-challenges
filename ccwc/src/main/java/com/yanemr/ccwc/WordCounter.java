package com.yanemr.ccwc;

import static picocli.CommandLine.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import com.yanemr.ccwc.dto.Result;
import com.yanemr.ccwc.output.OutputFormatter;
import com.yanemr.ccwc.output.OutputFormatterFactory;
import com.yanemr.ccwc.util.FileUtils;

@Command(name = "ccwc")
public class WordCounter implements Callable<Result> {

    @Option(names = {"-c", "--bytes"})
    boolean countBytes;

    @Option(names = {"-m", "--chars"})
    boolean countChars;

    @Option(names = {"-l", "--lines"})
    boolean countLines;

    @Option(names = {"-w", "--words"})
    boolean countWords;

    @Parameters(index = "0")
    private String file;

    @Override
    public Result call() throws Exception {

        String currentDirectory = System.getProperty("user.dir");
        var bytes = FileUtils.readFile(Paths.get(currentDirectory, file).toString());
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
                boolean whitespace = Character.isWhitespace(bytes[i]);
                boolean whitespace1 = Character.isWhitespace(bytes[i - 1]);
                if (whitespace && !whitespace1) {
                    ++result.numberOfWords;
                }
            }
        }
        OutputFormatter formatter = OutputFormatterFactory.getInstance();
        System.out.println(formatter.format(result));
        return result;
    }
}

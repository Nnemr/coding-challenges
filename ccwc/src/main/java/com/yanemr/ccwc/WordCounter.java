package com.yanemr.ccwc;

import com.yanemr.ccwc.dto.Result;
import com.yanemr.ccwc.output.OutputFormatter;
import com.yanemr.ccwc.output.OutputFormatterFactory;
import com.yanemr.ccwc.util.FileUtils;

import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

@Command(name = "ccwc")
public class WordCounter implements Callable<Result> {

    @Option(names = {"-c", "--bytes"})
    private boolean countBytes;

    @Option(names = {"-m", "--chars"})
    private boolean countChars;

    @Option(names = {"-l", "--lines"})
    private boolean countLines;

    @Option(names = {"-w", "--words"})
    private boolean countWords;

    @Parameters(index = "0", defaultValue = "", arity = "0")
    private String file;

    @Override
    public Result call() throws Exception {

        String currentDirectory = System.getProperty("user.dir");
        byte[] bytes = null;
        var result = new Result(file);

        boolean switchAll = (countWords == countLines) && (countBytes == countLines);

        if (switchAll) {
            countWords = true;
            countBytes = true;
            countLines = true;
        }

        if (file.isBlank()) {
            bytes = FileUtils.readInput();

        } else {
            bytes = FileUtils.readFile(Paths.get(currentDirectory, file).toString());
        }
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

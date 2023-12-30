package com.yanemr.ccwc;

import com.yanemr.ccwc.dto.Result;
import picocli.CommandLine;

public class CCWC {

    public static void main(String[] args) {
        WordCounter wordCounter = new WordCounter();
        var commandLine = new CommandLine(wordCounter);
        Result result = new Result();
        commandLine.setExecutionResult(result);
        commandLine.execute(args);
    }
}

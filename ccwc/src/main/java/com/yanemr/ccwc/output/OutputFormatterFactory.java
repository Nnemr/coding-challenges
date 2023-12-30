package com.yanemr.ccwc.output;

public class OutputFormatterFactory {

    public static OutputFormatter getInstance() {
        return new OutputFormatterImpl();
    }
}

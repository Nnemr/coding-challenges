package com.yanemr.ccwc.dto;

public class Result {

    public String fileName;
    public int numberOfBytes;
    public int numberOfChars;
    public int numberOfLines;
    public int numberOfWords;

    public Result() {
        this("");
    }
    public Result(String fileName) {
        this.fileName = fileName;

        this.numberOfLines = -1;
        this.numberOfChars = -1;
        this.numberOfWords = -1;
        this.numberOfBytes = -1;
    }
}

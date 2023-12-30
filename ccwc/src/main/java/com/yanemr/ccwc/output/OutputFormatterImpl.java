package com.yanemr.ccwc.output;

import com.yanemr.ccwc.dto.Result;

class OutputFormatterImpl implements OutputFormatter {
    @Override
    public String format(Result result) {
        StringBuilder outputBuilder = new StringBuilder();
        if (result.numberOfBytes != -1) {
            outputBuilder.append(result.numberOfBytes).append(" ");
        }

        if (result.numberOfChars != -1) {
            outputBuilder.append(result.numberOfChars).append(" ");
        }

        if (result.numberOfLines != -1) {
            outputBuilder.append(result.numberOfLines).append(" ");
        }

        if (result.numberOfWords != -1) {
            outputBuilder.append(result.numberOfWords).append(" ");
        }
        outputBuilder.append(result.fileName);
        return outputBuilder.toString();
    }
}

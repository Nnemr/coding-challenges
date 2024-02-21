package org.yanemr.ccjq;

import org.yanemr.ccjq.utils.InputUtil;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = args[0].equals(".") ? InputUtil.readInputStream() : InputUtil.readFile(args[0]);
        Ccjq ccjq = new Ccjq();
        ccjq.parse(input);
    }

}

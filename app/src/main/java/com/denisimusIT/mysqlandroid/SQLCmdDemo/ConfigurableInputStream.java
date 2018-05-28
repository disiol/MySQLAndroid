package com.denisimusIT.mysqlandroid.SQLCmdDemo;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurableInputStream extends InputStream {
    private String newLine = System.lineSeparator();
    private String line;
    private boolean endLine = false;
    private String printed;

    private static final String NEWLINE = System.lineSeparator();

    @Override
    public int read() throws IOException {

        if (line.length() == 0) {
            printInput();
            return -1;
        }


        if (endLine) {
            endLine = false;
            printInput();
            return -1;
        }

        char ch = line.charAt(0);
        line = line.substring(1);


        if (newLine.charAt(0) == ch) {
            endLine = true;
        } else {
            printed += ch;
        }


        return (int) ch;
    }


    private void printInput() {
        System.out.println(newLine +"Input: " + printed + newLine + "Output: ");
        printed = "";
    }

    public void add(String line) {
        if (this.line == null) {
            this.line = line + newLine;
        } else {
            this.line += line + newLine;


        }
    }


    @Override
    public synchronized void reset() throws IOException {
        line = null;
        endLine = false;
    }

}

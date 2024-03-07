package ru.nsu.dmustakaev;

import java.io.*;

public class CLIParser {
    private String arg;

    CLIParser(String[] argv) throws IllegalArgumentException {
        if(argv.length >= 2) {
            throw new IllegalArgumentException("The number of CLI args must be 1 or 0");
        }
        arg = (argv.length == 1) ? argv[0] : null;
    }
    public boolean hasArg() {
        return arg != null;
    }

    public String getArg() {
        return arg;
    }
}

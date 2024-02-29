package ru.nsu.dmustakaev;

import java.util.ArrayList;
import java.util.List;

public class Instruction {
    private final String command;
    private final List<String> args;

    Instruction(String command, List<String> args) {
        this.command = command;
        this.args = args;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArgs() {
        return args;
    }
}

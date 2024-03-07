package ru.nsu.dmustakaev.commands;

import ru.nsu.dmustakaev.context.Context;

import java.util.List;

public class Pop implements Command {
    @Override
    public void run(List<String> args, Context context) {
        context.popStack();
    }
}

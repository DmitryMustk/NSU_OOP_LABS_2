package ru.nsu.dmustakaev.commands;

import ru.nsu.dmustakaev.context.Context;

import java.util.List;

public class Addition implements Command{
    @Override
    public void run(List<String> args, Context context) {
        Double x1 = context.popStack();
        Double x2 = context.popStack();
        context.putOnStack(x1 + x2);
    }
}

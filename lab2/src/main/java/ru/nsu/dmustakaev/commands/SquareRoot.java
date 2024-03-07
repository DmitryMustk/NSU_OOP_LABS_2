package ru.nsu.dmustakaev.commands;

import ru.nsu.dmustakaev.context.Context;

import java.util.List;

public class SquareRoot implements Command {
    @Override
    public void run(List<String> args, Context context) {
        context.putOnStack(Math.sqrt(context.popStack()));
    }
}

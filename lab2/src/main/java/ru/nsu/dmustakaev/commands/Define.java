package ru.nsu.dmustakaev.commands;

import ru.nsu.dmustakaev.context.Context;

import java.util.List;

public class Define implements Command{
    @Override
    public void run(List<String> args, Context context) {
        context.addVariable(args.get(0), Double.valueOf(args.get(1)));
    }
}

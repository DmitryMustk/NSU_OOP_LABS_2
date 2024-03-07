package ru.nsu.dmustakaev.commands;

import ru.nsu.dmustakaev.context.Context;

import java.util.List;

public class Push implements Command{
    @Override
    public void run(List<String> args, Context context) {
        String value = args.getFirst();
        if(context.isCorrectVariableName(value)) {
            context.putOnStack(context.getValueByVariable(value));
            return;
        }
        context.putOnStack(Double.valueOf(value));
    }
}

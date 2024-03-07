package ru.nsu.dmustakaev;

import ru.nsu.dmustakaev.commands.Command;
import ru.nsu.dmustakaev.context.Context;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class StackCalculator {

    private Context context;
    private CommandFactory cmdFactory;

    StackCalculator() throws IOException {
        context = new Context();
        cmdFactory = new CommandFactory();
    }

    void calculate(List<Instruction> instructions) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        for(var instruction : instructions) {
            Command cmd = cmdFactory.create(instruction.getCommand());
            cmd.run(instruction.getArgs(), context);
        }
    }
}

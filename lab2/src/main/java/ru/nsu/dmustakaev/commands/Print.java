package ru.nsu.dmustakaev.commands;

import ru.nsu.dmustakaev.context.Context;

import java.util.List;

public class Print implements Command {
    @Override
    public void run(List<String> args, Context context) {
        System.out.println(context.peekStack());
    }
}

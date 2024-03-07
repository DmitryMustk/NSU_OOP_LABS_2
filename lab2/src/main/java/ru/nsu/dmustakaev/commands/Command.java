package ru.nsu.dmustakaev.commands;

import ru.nsu.dmustakaev.context.Context;

import java.util.List;

public interface Command {
    void run(List<String> args, Context context);
}

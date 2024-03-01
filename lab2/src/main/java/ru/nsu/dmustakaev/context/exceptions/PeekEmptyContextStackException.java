package ru.nsu.dmustakaev.context.exceptions;

import java.util.EmptyStackException;

public class PeekEmptyContextStackException extends RuntimeException {
    public PeekEmptyContextStackException() {
        super("Attempt to peak an empty stack");
    }
}

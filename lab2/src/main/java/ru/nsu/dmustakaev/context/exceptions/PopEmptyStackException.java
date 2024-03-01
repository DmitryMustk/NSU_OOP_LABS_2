package ru.nsu.dmustakaev.context.exceptions;

public class PopEmptyStackException extends RuntimeException {
    public PopEmptyStackException() {
        super("Attempt to pop an empty stack");
    }
}

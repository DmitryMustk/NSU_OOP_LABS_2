package ru.nsu.dmustakaev.context.exceptions;

public class VariableRewriteException extends RuntimeException {
    public VariableRewriteException() {
        super("Attempt to rewrite the variable");
    }
}

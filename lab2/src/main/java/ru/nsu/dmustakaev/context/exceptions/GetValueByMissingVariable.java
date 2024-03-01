package ru.nsu.dmustakaev.context.exceptions;

public class GetValueByMissingVariable extends RuntimeException {
    public GetValueByMissingVariable() {
        super("Attempt to get a missing variable from context");
    }
}

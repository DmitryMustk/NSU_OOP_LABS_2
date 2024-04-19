package ru.nsu.dmustakaev.game_object_factory;

public class ResourceException extends RuntimeException {
    public ResourceException() {
        super("Can't open resource file");
    }
}

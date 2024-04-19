package ru.nsu.dmustakaev.game_object_factory;

public class ClassLoaderException extends RuntimeException {
    public ClassLoaderException() {
        super("Class is not load");
    }
}

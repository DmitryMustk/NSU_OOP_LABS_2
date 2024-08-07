package ru.nsu.dmustakaev.client;

@FunctionalInterface
public interface MessageHandler {
    void handleMessage(String message);
}

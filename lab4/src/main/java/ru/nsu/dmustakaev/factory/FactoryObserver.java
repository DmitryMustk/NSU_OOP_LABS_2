package ru.nsu.dmustakaev.factory;

public interface FactoryObserver {
    void onFactoryStarted();

    void onFactoryShutdown();
}

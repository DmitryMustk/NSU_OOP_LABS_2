package ru.nsu.dmustakaev.threadpool;

public interface Task {
    String getName();
    void performWork() throws InterruptedException;
}

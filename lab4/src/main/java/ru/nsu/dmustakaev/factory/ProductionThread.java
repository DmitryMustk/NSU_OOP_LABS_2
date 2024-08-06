package ru.nsu.dmustakaev.factory;

import ru.nsu.dmustakaev.factory.store.Store;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProductionThread<T> extends Thread {
    private final Supplier<T> supplier;
    private final Consumer<T> store;
    private volatile boolean isRunning;

    public ProductionThread(Supplier<T> supplier, Consumer<T> store) {
        this.supplier = supplier;
        this.store = store;
        this.isRunning = true;
    }

    public void shutdown() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            T item = supplier.get();
            if (item == null) {
                continue;
            }
            store.accept(item);
        }
    }
}

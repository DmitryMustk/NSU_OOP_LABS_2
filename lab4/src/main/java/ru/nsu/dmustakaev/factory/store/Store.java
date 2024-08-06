package ru.nsu.dmustakaev.factory.store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.dmustakaev.factory.utils.LockingQueue;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Store<T> implements Supplier<T>, Consumer<T> {
    private static final Logger logger = LogManager.getLogger(Store.class);
    private final int storeCapacity;
    private final LockingQueue<T> store;

    public Store(int storeCapacity) {
        this.storeCapacity = storeCapacity;
        this.store = new LockingQueue<>(storeCapacity);
    }

    @Override
    public T get() {
        try {
            return store.take();
        } catch (InterruptedException e) {
            logger.info("Caught InterruptedException, returning null");
            return null;
        }
    }

    @Override
    public void accept(T part) {
        try {
            store.put(part);
        } catch (InterruptedException e) {
            logger.info("Can't put because of InterruptedException");
        }
    }

    public int getSize() {
        return store.size();
    }

    public int getCapacity() {
        return storeCapacity;
    }
}

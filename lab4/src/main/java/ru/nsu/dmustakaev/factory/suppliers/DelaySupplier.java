package ru.nsu.dmustakaev.factory.suppliers;

import java.util.function.Supplier;

public abstract class DelaySupplier<T> implements Supplier<T> {
    private volatile int delay;

    public DelaySupplier(int delay) {
        this.delay = delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }
}

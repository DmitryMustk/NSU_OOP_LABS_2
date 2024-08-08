package ru.nsu.dmustakaev.gui.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.dmustakaev.factory.parts.Car;
import ru.nsu.dmustakaev.factory.store.Dealer;
import ru.nsu.dmustakaev.threadpool.Task;
import ru.nsu.dmustakaev.threadpool.ThreadPool;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class DealController extends Thread {
    private static final Logger logger = LogManager.getLogger(DealController.class);

    private static final int QUEUE_SIZE = 1000;

    private final Object monitor = new Object();

    private final Supplier<Car> outputStore;
    private final ThreadPool threadPool;
    private final AtomicInteger totalSold = new AtomicInteger(0);
    private BigDecimal totalGain = BigDecimal.ZERO;

    private final Dealer dealer;
    private volatile boolean isRunning = true;

    public DealController(Supplier<Car> outputStore, Dealer dealer, int numOfThreads) {
        this.outputStore = outputStore;
        this.dealer = dealer;

        threadPool = new ThreadPool(numOfThreads, QUEUE_SIZE);
    }

    public int getTotalSold() {
        return totalSold.intValue();
    }

    public BigDecimal getTotalGain() {
        synchronized (monitor) {
            return totalGain;
        }
    }

    private void addMoney(BigDecimal gain) {
        synchronized (monitor) {
            totalGain = totalGain.add(gain);
        }
    }

    public void shutdown() {
        if (!isRunning) {
            throw new RuntimeException("double shutdown detected");
        }
        isRunning = false;
        threadPool.shutdown();
    }

    @Override
    public void run() {
        while (isRunning) {
            Car car = outputStore.get();
            if (car == null) {
                continue;
            }

            Task task = new Task() {
                @Override
                public String getName() {
                    return "Sell a car";
                }

                @Override
                public void performWork() throws InterruptedException {
                    totalSold.incrementAndGet();
                    addMoney(dealer.sell(car));
                    logger.info("Sold car to dealer %s: %s".formatted(Thread.currentThread().getName(), car));
                }
            };

            try {
                threadPool.addTask(task);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
    }
}

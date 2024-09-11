package ru.nsu.dmustakaev.gui.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.dmustakaev.factory.FactoryObserver;
import ru.nsu.dmustakaev.factory.parts.Accessory;
import ru.nsu.dmustakaev.factory.parts.Body;
import ru.nsu.dmustakaev.factory.parts.Car;
import ru.nsu.dmustakaev.factory.parts.Engine;
import ru.nsu.dmustakaev.threadpool.Task;
import ru.nsu.dmustakaev.threadpool.ThreadPool;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BuildController extends Thread implements FactoryObserver {
    private static final Logger logger = LogManager.getLogger(BuildController.class);

    private static final int QUEUE_SIZE = 1000;

    private final Supplier<Body> bodyStore;
    private final Supplier<Engine> engineStore;
    private final Supplier<Accessory> accessoryStore;
    private final Consumer<Car> outputStore;

    private final ThreadPool threadPool;
    private final Object pauseObject = new Object();
    private volatile boolean isRunning = true;
    private volatile boolean pause = false;

    public BuildController(
            Supplier<Body> bodyStore,
            Supplier<Engine> engineStore,
            Supplier<Accessory> accessoryStore,
            Consumer<Car> outputStore,
            int numOfBuilders
    ) {
        this.bodyStore = bodyStore;
        this.engineStore = engineStore;
        this.accessoryStore = accessoryStore;
        this.outputStore = outputStore;

        threadPool = new ThreadPool(numOfBuilders, QUEUE_SIZE);
    }

    public boolean isPause() {
        return pause;
    }

    public void pauseProduction() {
        if (pause) {
            throw new RuntimeException("double pause detected");
        }
        pause = true;
    }

    public void continueProduction() {
        if (!pause) {
            throw new RuntimeException("double continue detected");
        }
        pause = false;
        synchronized (pauseObject) {
            pauseObject.notifyAll();
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
            Body body = bodyStore.get();
            Engine engine = engineStore.get();
            Accessory accessory = accessoryStore.get();

            if (body == null || engine == null || accessory == null) {
                continue;
            }

            Task task = new Task() {
                @Override
                public String getName() {
                    return "Build a car";
                }

                @Override
                public void performWork() throws InterruptedException {
                    Car car = new Car(accessory, body, engine);
                    outputStore.accept(car);
                }
            };

            try {
                threadPool.addTask(task);
            } catch (InterruptedException e) {
                logger.error(e);
            }

            if (pause) {
                synchronized (pauseObject) {
                    try {
                        do {
                            pauseObject.wait();
                        } while (pause);
                    } catch (InterruptedException ignored) {
                        logger.info("Interrupted exception ignored");
                    }
                }
            }
        }
    }

    @Override
    public void onFactoryStarted() {
        this.start();
    }

    @Override
    public void onFactoryShutdown() {
        this.shutdown();
    }
}

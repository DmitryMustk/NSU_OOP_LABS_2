package ru.nsu.dmustakaev.gui.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.dmustakaev.factory.parts.Accessory;
import ru.nsu.dmustakaev.factory.parts.Body;
import ru.nsu.dmustakaev.factory.parts.Car;
import ru.nsu.dmustakaev.factory.parts.Engine;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class BuildController extends Thread {
    private static final Logger logger = LogManager.getLogger(BuildController.class);

    private static final int QUEUE_SIZE = 1000;

    private final Supplier<Body> bodyStore;
    private final Supplier<Engine> engineStore;
    private final Supplier<Accessory> accessoryStore;
    private final Consumer<Car> outputStore;
}

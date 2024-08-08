package ru.nsu.dmustakaev;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.dmustakaev.factory.Factory;
import ru.nsu.dmustakaev.gui.FactoryGui;

import java.io.IOException;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        try {
            FactoryGui.start();
        } catch (IOException e) {
            logger.error(e);
            throw e;
        }
    }
}
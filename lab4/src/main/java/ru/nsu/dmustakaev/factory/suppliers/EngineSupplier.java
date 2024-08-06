package ru.nsu.dmustakaev.factory.suppliers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.dmustakaev.factory.parts.Engine;

public class EngineSupplier extends DelaySupplier<Engine> {
    private static final Logger logger = LogManager.getLogger(EngineSupplier.class);

    public EngineSupplier(int delay) {
        super(delay);
    }

    @Override
    public Engine get() {
        try {
            Thread.sleep(getDelay());
        } catch (InterruptedException e) {
            logger.info("Caught InterruptedException, returning null");
            return null;
        }
        return new Engine();
    }
}

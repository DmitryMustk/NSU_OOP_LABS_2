package ru.nsu.dmustakaev.factory.suppliers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.dmustakaev.factory.parts.Body;

public class BodySupplier extends DelaySupplier<Body> {
    private static final Logger logger = LogManager.getLogger(BodySupplier.class);

    public BodySupplier(int delay) {
        super(delay);
    }

    @Override
    public Body get() {
        try {
            Thread.sleep(getDelay());
        } catch (InterruptedException e) {
            logger.info("Caught InterruptedException, returning null");
            return null;
        }
        return new Body();
    }
}

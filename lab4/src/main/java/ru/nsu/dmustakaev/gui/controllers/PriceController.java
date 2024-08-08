package ru.nsu.dmustakaev.gui.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.dmustakaev.factory.store.Dealer;

import java.math.BigDecimal;

public class PriceController extends Thread {
    private static final Logger logger = LogManager.getLogger(PriceController.class);

    private static final int ONE_SECOND = 1000;

    private static final int OK_CAR_PRICE = 500;

    private final Dealer dealer;
    private final FactoryProductionControlAdapter adapter;

    public PriceController(Dealer dealer, FactoryProductionControlAdapter adapter) {
        this.dealer = dealer;
        this.adapter = adapter;
        this.setDaemon(true);
    }

    public interface FactoryProductionControlAdapter {
        boolean isPaused();
        void pauseProduction();
        void continueProduction();
    }

    private boolean isCarPriceOk(BigDecimal carPrice) {
        return carPrice.compareTo(BigDecimal.valueOf(OK_CAR_PRICE)) >= 0;
    }

    @Override
    public void run() {
        while (true) {
            BigDecimal carPrice = dealer.getCarPrice();
            if (isCarPriceOk(carPrice)) {
                logger.info(carPrice);
                if (adapter.isPaused()) {
                    logger.info("Car price is ok: %s; starting production back...".formatted(carPrice));
                    adapter.continueProduction();
                }
            } else if (!adapter.isPaused()) {
                logger.info("Car price is not ok: %s; stopping production...".formatted(carPrice));
                adapter.pauseProduction();
            }

            try {
                Thread.sleep(ONE_SECOND);
            } catch (InterruptedException e) {
                logger.warn(e);
                break;
            }
        }
    }
}

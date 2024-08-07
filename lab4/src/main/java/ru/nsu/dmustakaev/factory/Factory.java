package ru.nsu.dmustakaev.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.dmustakaev.factory.parts.Accessory;
import ru.nsu.dmustakaev.factory.parts.Body;
import ru.nsu.dmustakaev.factory.parts.Car;
import ru.nsu.dmustakaev.factory.parts.Engine;
import ru.nsu.dmustakaev.factory.store.Dealer;
import ru.nsu.dmustakaev.factory.store.Store;
import ru.nsu.dmustakaev.factory.suppliers.AccessorySupplier;
import ru.nsu.dmustakaev.factory.suppliers.BodySupplier;
import ru.nsu.dmustakaev.factory.suppliers.EngineSupplier;
import ru.nsu.dmustakaev.gui.controllers.BuildController;
import ru.nsu.dmustakaev.gui.controllers.DealController;
import ru.nsu.dmustakaev.gui.controllers.PriceController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

public class Factory {
    private final static Logger logger = LogManager.getLogger(Factory.class);

    private final static String propertiesFileName = "/factory.properties";

    private final BodySupplier bodySupplier;
    private final EngineSupplier engineSupplier;
    private final AccessorySupplier accessorySupplier;

    private final Store<Body> bodyStore;
    private final Store<Engine> engineStore;
    private final Store<Accessory> accessoryStore;
    private final Store<Car> carStore;

    private final ProductionThread<Body> bodyProductionThread;
    private final ProductionThread<Engine> engineProductionThread;
    private final ProductionThread<Accessory> accessoryProductionThread;

    private final BuildController buildController;
    private final DealController dealController;
    private final PriceController priceController;

    private final Dealer dealer;

    public Factory() throws IOException {
        Properties properties = new Properties();

        try {
            properties.load(getClass().getResourceAsStream(propertiesFileName));
        } catch (IOException e){
            logger.error("Can't load " + propertiesFileName);
            throw e;
        }

        carStore = new Store<>(Integer.parseInt((String) properties.get("CAR.STORE_SIZE")));

        bodySupplier = new BodySupplier(Integer.parseInt((String) properties.get("CAR_BODY.DELAY")));
        bodyStore = new Store<>(Integer.parseInt((String) properties.get("CAR_BODY.STORE_SIZE")));
        bodyProductionThread = new ProductionThread<>(bodySupplier, bodyStore);

        engineSupplier = new EngineSupplier(Integer.parseInt((String) properties.get("CAR_ENGINE.DELAY")));
        engineStore = new Store<>(Integer.parseInt((String) properties.get("CAR_ENGINE.STORE_SIZE")));
        engineProductionThread = new ProductionThread<>(engineSupplier, engineStore);

        accessorySupplier = new AccessorySupplier(Integer.parseInt((String) properties.get("CAR_ACCESSORY.DELAY")));
        accessoryStore = new Store<>(Integer.parseInt((String) properties.get("CAR_ACCESSORY.STORE_SIZE")));
        accessoryProductionThread = new ProductionThread<>(accessorySupplier, accessoryStore);

        dealer = new Dealer();

        int dealersCount = Integer.parseInt((String) properties.get("DEALER.NUM"));
        int workersCount = Integer.parseInt((String) properties.get("WORKER.NUM"));

        logger.info("Stores, suppliers and threads created");

        buildController = new BuildController(bodyStore, engineStore, accessoryStore, carStore, workersCount);
        priceController = new PriceController(dealer, new PriceController.FactoryProductionControlAdapter() {
            @Override
            public boolean isPaused() {
                return buildController.isPause();
            }

            @Override
            public void pauseProduction() {
                buildController.pauseProduction();
            }

            @Override
            public void continueProduction() {
                buildController.continueProduction();
            }
        });

        dealController = new DealController(carStore, dealer, dealersCount);

        logger.info("Controllers created");
    }

    public void start() {
        bodyProductionThread.start();
        engineProductionThread.start();
        accessoryProductionThread.start();

        logger.info("Production threads started");

        buildController.start();
        dealController.start();
        priceController.start();

        logger.info("Controllers started");

    }

    public void shutdown() {
        logger.info("Shutdown...");

        bodyProductionThread.shutdown();
        engineProductionThread.shutdown();
        accessoryProductionThread.shutdown();

        buildController.shutdown();
        dealController.shutdown();
    }

    public BodySupplier getBodySupplier() {
        return bodySupplier;
    }

    public EngineSupplier getEngineSupplier() {
        return engineSupplier;
    }

    public AccessorySupplier getAccessorySupplier() {
        return accessorySupplier;
    }

    public Store<Body> getBodyStore() {
        return bodyStore;
    }

    public Store<Engine> getEngineStore() {
        return engineStore;
    }

    public Store<Accessory> getAccessoryStore() {
        return accessoryStore;
    }

    public Store<Car> getCarStore() {
        return carStore;
    }

    public ProductionThread<Body> getBodyProductionThread() {
        return bodyProductionThread;
    }

    public ProductionThread<Engine> getEngineProductionThread() {
        return engineProductionThread;
    }

    public ProductionThread<Accessory> getAccessoryProductionThread() {
        return accessoryProductionThread;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setBodySupplierDelay(int delay) {
        bodySupplier.setDelay(delay);
    }

    public void setEngineSupplierDelay(int delay) {
        engineSupplier.setDelay(delay);
    }

    public void setAccessorySupplierDelay(int delay) {
        accessorySupplier.setDelay(delay);
    }

    public int getTotalSold() {
        return dealController.getTotalSold();
    }

    public BigDecimal getTotalGain() {
        return dealController.getTotalGain();
    }

    public boolean isBuildingPaused() {
        return buildController.isPause();
    }
}

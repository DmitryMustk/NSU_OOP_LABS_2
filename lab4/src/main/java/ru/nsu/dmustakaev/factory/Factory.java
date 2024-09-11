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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    private final int dealersCount;
    private final int workersCount;

    private final Dealer dealer;

    private final List<FactoryObserver> observers = new ArrayList<>();

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

        dealersCount = Integer.parseInt((String) properties.get("DEALER.NUM"));
        workersCount = Integer.parseInt((String) properties.get("WORKER.NUM"));

        logger.info("Stores, suppliers and threads created");
    }

    public void start() {
        bodyProductionThread.start();
        engineProductionThread.start();
        accessoryProductionThread.start();

        logger.info("Production threads started");
        notifyObserversOnStart();
    }

    public void shutdown() {
        logger.info("Shutdown...");

        bodyProductionThread.shutdown();
        engineProductionThread.shutdown();
        accessoryProductionThread.shutdown();

        notifyObserversOnShutdown();
    }

    public void addObserver(FactoryObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(FactoryObserver observer) {
        observers.remove(observer);
    }

    private void notifyObserversOnStart() {
        observers.forEach(observer -> {observer.onFactoryStarted();});
    }

    private void notifyObserversOnShutdown() {
        observers.forEach(observer -> {observer.onFactoryShutdown();});
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

    public int getWorkersCount() {
        return workersCount;
    }

    public int getDealersCount() {
        return dealersCount;
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
}

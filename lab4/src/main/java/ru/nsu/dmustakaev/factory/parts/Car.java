package ru.nsu.dmustakaev.factory.parts;

public class Car {
    private final Accessory accessory;
    private final Body body;
    private final Engine engine;

    public Car(Body body, Engine engine, Accessory accessory) {
        this.body = body;
        this.engine = engine;
        this.accessory = accessory;
    }

    @Override
    public String toString() {
        return "Car %d (Body: %d, Engine: %d, Accessory: %d)"
                .formatted(
                        this.hashCode(),
                        this.body.hashCode(),
                        this.engine.hashCode(),
                        this.accessory.hashCode()
                );
    }
}

package ru.nsu.dmustakaev.factory.product;

public class Car extends Product {
    private final Engine engine;
    private final Body body;
    private final Аccessory аccessory;


    public Car(Engine engine, Body body, Аccessory аccessory) {
        super("Car");
        this.engine = engine;
        this.body = body;
        this.аccessory = аccessory;
    }

    public Engine getEngine() {
        return engine;
    }

    public Body getBody() {
        return body;
    }

    public Аccessory getSeatCover() {
        return аccessory;
    }
}

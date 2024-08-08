package ru.nsu.dmustakaev.factory.parts;

public record Car (Accessory accessory, Body body, Engine engine) {
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

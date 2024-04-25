package ru.nsu.dmustakaev.factory.product;

import java.util.UUID;

public class Product {
    private final UUID id = UUID.randomUUID();
    private final String name;

    public Product(String name) {
        this.name = name;
    }

    UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

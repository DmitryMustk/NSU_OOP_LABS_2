package ru.nsu.dmustakaev;

import ru.nsu.dmustakaev.factory.Factory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Factory factory = new Factory();
        factory.start();
    }
}
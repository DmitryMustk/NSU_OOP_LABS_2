package ru.nsu.dmustakaev.gui;

import java.io.IOException;

public class FactoryGui {
    public static void start() throws IOException {
        FactoryWindow window = new FactoryWindow();
        window.start();
    }
}

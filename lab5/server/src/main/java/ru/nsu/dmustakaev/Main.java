package ru.nsu.dmustakaev;

public class Main {
    public static void main(String[] args) {
        Server server = new Server("localhost", 5585, 15);
        server.start();
    }
}

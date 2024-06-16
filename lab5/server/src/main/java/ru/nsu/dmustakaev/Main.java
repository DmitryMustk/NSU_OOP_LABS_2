package ru.nsu.dmustakaev;

public class Main {
    public static void main(String[] args) {
        Server server = new Server("localhost", 5555, 15);
        server.start();
    }
}

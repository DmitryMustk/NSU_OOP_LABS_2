package ru.nsu.dmustakaev;

public class Main {
    public static void main(String[] args) {
        Server server = new Server("localhost", 5556, 15);
        server.start();
    }
    //TODO: fix server death from client terminating
}

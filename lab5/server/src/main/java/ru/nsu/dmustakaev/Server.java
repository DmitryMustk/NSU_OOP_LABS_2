package ru.nsu.dmustakaev;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {
    private final Logger logger = Logger.getLogger(Server.class.getName());
    private final String host;
    private final int port;
    private final int connectionsCount;
    private final ExecutorService executorService;
    private final Chat chat;


    public Server(String host, int port, int connectionsCount) {
        this.host = host;
        this.port = port;
        this.connectionsCount = connectionsCount;
        executorService = Executors.newFixedThreadPool(connectionsCount);
        chat = new Chat("chatTest", 100L, 5L);
    }

    public void start() {
        logger.info("Starting server...");
        try (ServerSocket serverSocket = new ServerSocket(port, connectionsCount)){
            Thread stopThread = createStopThread();
            stopThread.start();
            while (stopThread.isAlive()) {
                try {
                    Socket socket = serverSocket.accept();
                    logger.info("New connection from " + socket.getRemoteSocketAddress());
                    Connection connection = new Connection(chat, socket);
                    executorService.submit(connection);
                } catch (IOException | RuntimeException e) {
                    logger.warning(e.getMessage());
                }
            }
            executorService.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Thread createStopThread() {
        return new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                if (scanner.next().equals("stop")) {
                    return;
                }
            }
        });
    }
}

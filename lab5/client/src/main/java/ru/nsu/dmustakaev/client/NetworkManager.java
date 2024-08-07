package ru.nsu.dmustakaev.client;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class NetworkManager {
    private Socket socket;
    private DataOutputStream writer;
    private DataInputStream reader;

    private RequestCommands lastCommand;
    private final Logger logger = Logger.getLogger(NetworkManager.class.getName());

    public void connect(String host, int port, MessageHandler messageHandler) {
        try {
            socket = new Socket(host, port);
            writer = new DataOutputStream(socket.getOutputStream());
            reader = new DataInputStream(socket.getInputStream());
            new Thread(() -> listenForMessages(messageHandler)).start();
        } catch (IOException e) {
            showAlert("Failed to connect to server: " + e.getMessage());
        }
    }

    public void sendMessage(String command) {
        try {
            byte[] messageBytes = command.getBytes(StandardCharsets.UTF_8);
            writer.writeInt(messageBytes.length);
            writer.write(messageBytes);
            writer.flush();
        } catch (IOException e) {
            showAlert("Failed to send message: " + e.getMessage());
        }
    }

    public void listenForMessages(MessageHandler messageHandler) {
        try {
            while (!socket.isClosed()) {
                if (reader.available() > 0) {
                    int messageLength = reader.readInt();
                    byte[] messageBytes = new byte[messageLength];
                    reader.readFully(messageBytes);
                    String message = new String(messageBytes, StandardCharsets.UTF_8);
                    logger.info("Received message length: %d. Message: %s".formatted(messageLength, message));
                    messageHandler.handleMessage(message);
                }
            }
        } catch (IOException e) {
            if (!socket.isClosed()) {
                showAlert("Connection lost: " + e.getMessage());
            }
        } finally {
            closeSocket();
        }
    }

    public RequestCommands getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(RequestCommands command) {
        this.lastCommand = command;
    }

    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(message);
            alert.show();
        });
    }

    public void closeSocket() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                logger.info("Socket closed.");
            }
        } catch (IOException e) {
            logger.warning("Failed to close socket: " + e.getMessage());
        }
    }
}

package ru.nsu.dmustakaev.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import org.controlsfx.dialog.LoginDialog;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import static ru.nsu.dmustakaev.client.Requests.*;

public class ChatClientController {
    @FXML
    private ListView<String> userListView;
    @FXML
    private ListView<String> messageListView;
    @FXML
    private Button loginButton;
    @FXML
    private Button logoutButton;
    @FXML
    private TextArea inputArea;

    private Socket socket;
    private DataOutputStream writer;
    private DataInputStream reader;

    private final ObservableList<String> messages = FXCollections.observableArrayList();
    private final ObservableList<String> users = FXCollections.observableArrayList();

    private final Logger logger = Logger.getLogger(ChatClientController.class.getName());

    @FXML
    public void initialize() throws IOException {
        userListView.setItems(users);
        messageListView.setItems(messages);

        socket = new Socket("localhost", 5556);
        writer = new DataOutputStream(socket.getOutputStream());
        reader = new DataInputStream(socket.getInputStream());

        loginButton.setOnAction(event -> onClickLogin());
        logoutButton.setOnAction(event -> onClickLogout());
        logoutButton.setVisible(false);
    }

    @FXML
    public void onClickSendMessage() {
        String message = inputArea.getText().trim();
        if (!message.isEmpty()) {
            sendCommand(COMMAND_REQUEST.formatted("message", MESSAGE_REQUEST.formatted(message)));
            inputArea.clear();
        }
    }

    @FXML
    public void onClickLogin() {
        LoginDialog loginDialog = new LoginDialog(new Pair<>("", ""), authenticator -> {
            String username = authenticator.getKey();
            String password = authenticator.getValue();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            if (username.isEmpty() || password.isEmpty()) {
                errorAlert.setContentText("Please enter your username and password");
                errorAlert.show();
            }
            sendCommand(COMMAND_REQUEST.formatted(
                    "login", USERNAME_REQUEST.formatted(username) + PASSWORD_REQUEST.formatted(password)));

            new Thread(this::listenForMessages).start();
            loginButton.setVisible(false);
            logoutButton.setVisible(true);
            return null;
        });
        loginDialog.showAndWait();
    }

    @FXML
    public void onClickFindUsers() {
    }

    @FXML
    public void onClickLogout() {

    }

    private void sendCommand(String command) {
        try {
            byte[] messageBytes = command.getBytes(StandardCharsets.UTF_8);
            writer.writeInt(messageBytes.length);
            writer.write(messageBytes);
            writer.flush();
        } catch (IOException e) {
            showAlert("Failed to send message: " + e.getMessage());
        }
    }

    private void listenForMessages() {
        try {
            while (!socket.isClosed()) {
                if (reader.available() > 0) {
                    int messageLength = reader.readInt();
                    byte[] messageBytes = new byte[messageLength];
                    reader.readFully(messageBytes);
                    String message = new String(messageBytes, StandardCharsets.UTF_8);
                    logger.info("Received message length: %d. Message: %s".formatted(messageLength, message));
                    processServerMessage(message);
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

    private void processServerMessage(String message) {
        if (message.contains("<event name=\"message\">")) {
            String from = extractXmlValue(message, "from");
            String chatMessage = extractXmlValue(message, "message");
            Platform.runLater(() -> messages.add(from + ": " + chatMessage));
        } else if (message.contains("<event name=\"userlogin\">")) {
            String name = extractXmlValue(message, "name");
            Platform.runLater(() -> messages.add(name + " is logged in."));
        } else if (message.contains("<event name=\"userlogout\">")) {
            String name = extractXmlValue(message, "name");
            Platform.runLater(() -> users.remove(name));
        }
    }


    private String extractXmlValue(String xml, String tagName) {
        int start = xml.indexOf("<" + tagName + ">") + tagName.length() + 2;
        int end = xml.indexOf("</" + tagName + ">");
        if (start < 0 || end < 0) return null;
        return xml.substring(start, end);
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

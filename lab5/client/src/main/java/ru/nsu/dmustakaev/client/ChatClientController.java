package ru.nsu.dmustakaev.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import org.controlsfx.dialog.LoginDialog;

import java.util.logging.Logger;

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

    private final ObservableList<String> messages = FXCollections.observableArrayList();
    private final ObservableList<String> users = FXCollections.observableArrayList();
    private final Logger logger = Logger.getLogger(ChatClientController.class.getName());

    private final NetworkManager networkManager;
    private final XmlProcessor xmlProcessor;
    private final CommandBuilder commandBuilder;
    private boolean isLogged = false;

    public ChatClientController() {
        this.networkManager = new NetworkManager();
        this.xmlProcessor = new XmlProcessor();
        this.commandBuilder = new CommandBuilder();
    }

    @FXML
    public void initialize() {
        String ip = promptForIPAddress();
        int port = promptForPort();

        if (ip == null || ip.isEmpty()) {
            showAlert("IP address is required.");
            Platform.exit();
            return;
        }

        if (port == -1) {
            showAlert("Valid port is required.");
            Platform.exit();
            return;
        }

        networkManager.connect(ip, port, this::processServerMessage);
        userListView.setItems(users);
        messageListView.setItems(messages);
        logoutButton.setVisible(false);
    }

    private String promptForIPAddress() {
        TextInputDialog dialog = new TextInputDialog("localhost");
        dialog.setTitle("Server IP Address");
        dialog.setHeaderText("Enter the IP address of the server:");
        dialog.setContentText("IP Address:");

        return dialog.showAndWait().orElse(null);
    }

    private int promptForPort() {
        while (true) {
            TextInputDialog dialog = new TextInputDialog("5585");
            dialog.setTitle("Server Port");
            dialog.setHeaderText("Enter the port number of the server:");
            dialog.setContentText("Port:");

            String portStr = dialog.showAndWait().orElse(null);
            if (portStr == null) {
                showAlert("Port is required.");
                Platform.exit();
                return -1;
            }

            try {
                int port = Integer.parseInt(portStr);
                if (port > 0 && port <= 65535) {
                    return port;
                } else {
                    showAlert("Port number must be between 1 and 65535.");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid port number. Please enter a valid integer.");
            }
        }
    }



    @FXML
    public void onClickSendMessage() {
        checkLogging();
        String message = inputArea.getText().trim();
        if (!message.isEmpty()) {
            networkManager.setLastCommand(RequestCommands.MESSAGE);
            networkManager.sendMessage(commandBuilder.createMessageCommand(message));
            inputArea.clear();
        }
    }

    @FXML
    public void onClickLogin() {
        LoginDialog loginDialog = new LoginDialog(new Pair<>("", ""), authenticator -> {
            String username = authenticator.getKey();
            String password = authenticator.getValue();
            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Please enter your username and password");
                return null;
            }
            networkManager.setLastCommand(RequestCommands.LOGIN);
            networkManager.sendMessage(commandBuilder.createLoginCommand(username, password));
            new Thread(() -> networkManager.listenForMessages(this::processServerMessage)).start();
            loginButton.setVisible(false);
            logoutButton.setVisible(true);
            return null;
        });

        loginDialog.setResizable(false);
        loginDialog.getDialogPane().setMinHeight(ChatClient.SCREEN_HEIGHT);
        loginDialog.getDialogPane().setMaxHeight(ChatClient.SCREEN_HEIGHT);
        loginDialog.getDialogPane().setMinWidth(ChatClient.SCREEN_WIDTH);
        loginDialog.getDialogPane().setMaxWidth(ChatClient.SCREEN_WIDTH);

        loginDialog.showAndWait();
    }

    @FXML
    public void onClickFindUsers() {
        checkLogging();
        networkManager.setLastCommand(RequestCommands.LIST);
        networkManager.sendMessage(commandBuilder.createListCommand());
    }

    @FXML
    public void onClickLogout() {
        checkLogging();
        networkManager.setLastCommand(RequestCommands.LOGOUT);
        networkManager.sendMessage(commandBuilder.createLogoutCommand());
        logoutButton.setVisible(false);
        loginButton.setVisible(true);
    }

    private void checkLogging() {
        if (!isLogged) {
            showAlert("You are not logged in!");
        }
    }

    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(message);
            alert.show();
        });
    }

    private void processServerMessage(String message) {
        if (message.contains("<event name=\"message\">")) {
            String from = xmlProcessor.extractXmlValue(message, "from");
            String chatMessage = xmlProcessor.extractXmlValue(message, "message");
            Platform.runLater(() -> messages.add(from + ": " + chatMessage));

        } else if (message.contains("<event name=\"userlogin\">")) {
            String name = xmlProcessor.extractXmlValue(message, "name");
            Platform.runLater(() -> messages.add(name + " is logged in."));

        } else if (message.contains("<event name=\"userlogout\">")) {
            String name = xmlProcessor.extractXmlValue(message, "name");
            Platform.runLater(() -> {
                messages.add(name + " is logout.");
                users.remove(name);
            });

        } else if (message.contains("<success>")) {
            logger.info("Received success answer on %s request".formatted(networkManager.getLastCommand()));
            if (networkManager.getLastCommand() == RequestCommands.LIST) {
                Platform.runLater(() -> {
                    users.clear();
                    users.addAll(xmlProcessor.getUsernamesFromXML(message));
                });

            } else if (networkManager.getLastCommand() == RequestCommands.LOGIN) {
                isLogged = true;
                logger.info("Received success login message from server. User is logged");

            } else if (networkManager.getLastCommand() == RequestCommands.LOGOUT) {
                isLogged = false;
                logger.info("Received success logout message from server. User is logout");
            }
        }
    }

    @FXML
    public void onClickExit() {
        Platform.exit();
        System.exit(0);
    }

}

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
        userListView.setItems(users);
        messageListView.setItems(messages);
        networkManager.connect("localhost", 5556, this::processServerMessage);
        logoutButton.setVisible(false);
    }

    @FXML
    public void onClickSendMessage() {
        checkLogging();
        String message = inputArea.getText().trim();
        if (!message.isEmpty()) {
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
            networkManager.sendMessage(commandBuilder.createLoginCommand(username, password));
            new Thread(() -> networkManager.listenForMessages(this::processServerMessage)).start(); // исправлено здесь
            loginButton.setVisible(false);
            logoutButton.setVisible(true);
            return null;
        });
//        loginDialog.setHeight(ChatClient.SCREEN_HEIGHT);
//        loginDialog.setWidth(ChatClient.SCREEN_WIDTH);
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
        networkManager.sendMessage(commandBuilder.createListCommand());
    }

    @FXML
    public void onClickLogout() {
        checkLogging();
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
            Platform.runLater(() -> users.remove(name));
        } else if (message.contains("<success>")) {
            if (networkManager.getLastCommand() == RequestCommands.LIST) {
                Platform.runLater(() -> users.addAll(xmlProcessor.getUsernamesFromXML(message)));
            } else if (networkManager.getLastCommand() == RequestCommands.LOGIN) {
                isLogged = true;
            } else if (networkManager.getLastCommand() == RequestCommands.LOGOUT) {
                isLogged = false;
            }
        }
    }
}

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
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static ru.nsu.dmustakaev.client.RequestsTags.*;

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

    private boolean isLogged = false;
    private RequestCommand lastCommand;

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
        checkLoging();
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
            lastCommand = RequestCommand.LOGIN;
            return null;
        });
        loginDialog.showAndWait();
    }

    @FXML
    public void onClickFindUsers() {
        checkLoging();
        sendCommand(COMMAND_REQUEST.formatted("list", ""));
        lastCommand = RequestCommand.LIST;
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

    private void checkLoging() {
        if(!isLogged) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("You are not logged in!");
            alert.show();
        }
    }

    private List<String> getUsernamesFromXML(String xmlString) {
        List<String> usernames;
        try {
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xmlString)));
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("user");
            usernames = IntStream.range(0, nodeList.getLength())
                    .mapToObj(nodeList::item)
                    .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                    .map(node -> (Element) node)
                    .map(element -> {
                        Node nameNode = element.getElementsByTagName("name").item(0);
                        return nameNode != null ? nameNode.getTextContent() : null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return usernames;
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException(e);
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
        } else if (message.contains("<success>")) {
            if (lastCommand == RequestCommand.LOGIN) {
                isLogged = true;
            }
            Platform.runLater(() -> users.addAll(getUsernamesFromXML(message)));
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

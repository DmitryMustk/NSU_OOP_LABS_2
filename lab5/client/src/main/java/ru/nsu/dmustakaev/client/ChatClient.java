package ru.nsu.dmustakaev.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatClient extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chat_client.fxml"));
        stage.setTitle("Simple Chat");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    //TODO: norm exit
    //TODO: connection window
    //TODO: fix protocol issues
    //TODO: [BUG_FIX] user list button sejaet
    public static void main(String[] args) {
        launch(args);
    }
}

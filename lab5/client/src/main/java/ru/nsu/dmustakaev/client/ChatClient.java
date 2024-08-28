package ru.nsu.dmustakaev.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatClient extends Application {
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 400;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chat_client.fxml"));
        stage.setTitle("Simple Chat");

        stage.setResizable(true);
        stage.setMinWidth(SCREEN_WIDTH);
        stage.setMinHeight(SCREEN_HEIGHT);

        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

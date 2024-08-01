package ru.nsu.dmustakaev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.dmustakaev.controller.GameMenuController;

public class Main extends Application {
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu/GameMenu.fxml"));
        Parent mainPage = loader.load();

        GameMenuController menuController = loader.getController();
        menuController.setPrimaryStage(primaryStage);

        Scene scene = new Scene(mainPage, SCREEN_WIDTH, SCREEN_HEIGHT);
        primaryStage.setTitle("HeadBall");

        primaryStage.setResizable(false);
        primaryStage.setMinWidth(SCREEN_WIDTH);
        primaryStage.setMinHeight(SCREEN_HEIGHT);
        primaryStage.setMaxWidth(SCREEN_WIDTH);
        primaryStage.setMaxHeight(SCREEN_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//TODO: add pvp mode
    //TODO: add cvc mode
    //TODO: add pause
    //TODO: add settings
    //TODO: win screen
    //TODO: loose screen`
    //TODO: back to gameObject factory idea

//TODO: add game mode messages1
//TODO: balance sound


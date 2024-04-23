package ru.nsu.dmustakaev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class Main extends Application {
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 600;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent mainPage = FXMLLoader.load(getClass().getResource("/GameMenu.fxml"));
        Scene scene = new Scene(mainPage, SCREEN_WIDTH, SCREEN_HEIGHT);
        primaryStage.setTitle("HeadBall");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    //TODO: change the field texture
    //TODO: remove code repeating in view classes
    //TODO: remove all the magic numbers
    //TODO: add enemy
    //TODO: add enemy ai
    //TODO: add pvp mode
}





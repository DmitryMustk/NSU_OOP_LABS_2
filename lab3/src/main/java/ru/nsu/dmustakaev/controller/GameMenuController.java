package ru.nsu.dmustakaev.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.nsu.dmustakaev.utils.SoundEngine;

import java.io.IOException;

public class GameMenuController {
    @FXML
    public AnchorPane mainRoot;

    private SoundEngine soundEngine;
    private Stage primaryStage;

    @FXML
    public void initialize() {
        soundEngine = new SoundEngine();
        soundEngine.setMusic("/menu/sounds/menu_song.mp3");
        soundEngine.playMusic();
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    public void startGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game/GamePlay.fxml"));
        AnchorPane gamePlayRoot = fxmlLoader.load();

        GamePlayController gamePlayController = fxmlLoader.getController();
        gamePlayController.setPrimaryStage(primaryStage);
        gamePlayController.setSoundEngine(soundEngine);

        Scene gameScene = new Scene(gamePlayRoot);
        gamePlayController.setScene(gameScene);

        soundEngine.stopMusic();
        primaryStage.setScene(gameScene);
    }

    @FXML
    public void exitGame() {
        System.exit(0);
    }

    @FXML
    public void showMainMenu() {
        System.out.println("GOLOVA");
    }
}

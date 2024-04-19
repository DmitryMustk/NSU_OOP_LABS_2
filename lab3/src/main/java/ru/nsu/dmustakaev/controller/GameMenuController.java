package ru.nsu.dmustakaev.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ru.nsu.dmustakaev.SoundEngine;


public class GameMenuController {
    @FXML
    public AnchorPane mainRoot;
    private SoundEngine soundEngine;

    @FXML
    public void showMainMenu() {
        System.out.println("GOLOVA");
    }

    @FXML
    public void startGame() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GamePlay.fxml"));
        fxmlLoader.setController(new GamePlayController(soundEngine));
        GamePlayController gamePlayController = fxmlLoader.getController();
        gamePlayController.setScene(mainRoot.getScene());
        AnchorPane pane = fxmlLoader.load();
        mainRoot.getChildren().setAll(pane);
    }
    @FXML
    public void initialize() {
        soundEngine = new SoundEngine();
        soundEngine.setMusic("/menu_song.mp3");
        soundEngine.playMusic();
    }

    @FXML
    public void exitGame() {
        System.exit(0);
    }
}

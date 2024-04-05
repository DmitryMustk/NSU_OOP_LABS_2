package ru.nsu.dmustakaev.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import static ru.nsu.dmustakaev.Main.addMusic;
import static ru.nsu.dmustakaev.Main.mediaPlayer;

public class GameMenuController {
    @FXML
    public AnchorPane mainRoot;

    @FXML
    public void showMainMenu() {
        System.out.println("GOLOVA");
    }

    @FXML
    public void startGame() throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GamePlay.fxml"));
        fxmlLoader.setController(new GamePlayController());
        GamePlayController gamePlayController = fxmlLoader.getController();
        gamePlayController.setScene(mainRoot.getScene());
        AnchorPane pane = fxmlLoader.load();
        mainRoot.getChildren().setAll(pane);
    }
    @FXML
    public void initialize() {
        Media sound = new Media(getClass().getResource("/menu_song.mp3").toString());
        mediaPlayer = new MediaPlayer(sound);
        addMusic();
    }

    @FXML
    public void exitGame() {
        System.exit(0);
    }
}

package ru.nsu.dmustakaev.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PauseMenuController {

    public AnchorPane pauseMenuRoot;
    public Button resumeButton;
    public Button exitButton;
    private Stage stage;
    private Scene gameScene;

    public void setStageAndScene(Stage stage, Scene gameScene) {
        this.stage = stage;
        this.gameScene = gameScene;
    }

    @FXML
    private void resumeGame(ActionEvent event) {
        stage.setScene(gameScene);
    }

    @FXML
    private void exitToMainMenu(ActionEvent event) {
        // Здесь вы можете добавить логику для возврата к главному меню
        // Например, stage.setScene(mainMenuScene);
    }
}

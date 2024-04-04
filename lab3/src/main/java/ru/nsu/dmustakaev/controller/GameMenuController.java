package ru.nsu.dmustakaev.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

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
    public void exitGame() {
        System.exit(0);
    }
}

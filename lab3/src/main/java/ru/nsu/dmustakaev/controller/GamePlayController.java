package ru.nsu.dmustakaev.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.nsu.dmustakaev.GameEngine;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.SoundEngine;
import ru.nsu.dmustakaev.view.GameObjectView;

import java.io.IOException;

public class GamePlayController {
    public ImageView resumem;
    @FXML
    private AnchorPane gamePlayRoot;
    @FXML
    private ImageView background_game;
    @FXML
    private ImageView field;
    @FXML
    private Label scoreLabel;
    @FXML
    private AnchorPane pauseMenuRoot;

    private SoundEngine soundEngine;
    private GameEngine gameEngine;
    private Scene gameScene;
    private Stage primaryStage;

    public GamePlayController() {
    }

    @FXML
    public void initialize() {
        soundEngine = new SoundEngine();
        soundEngine.setMusic("/game/sounds/in_game_stadium_noises.wav");
        soundEngine.playMusic();

        gameEngine = new GameEngine(soundEngine);

        gamePlayRoot.getChildren().addAll(gameEngine
                .getGameObjectViews()
                .stream()
                .map(GameObjectView::getPane)
                .toList()
        );
        gamePlayRoot.requestFocus();
    }

    public void setSoundEngine(SoundEngine soundEngine) {
        this.soundEngine = soundEngine;
    }

    public void setScene(Scene scene, Stage primaryStage) {
        this.gameScene = scene;
        this.primaryStage = primaryStage;

        scene.setOnKeyPressed(event -> {
            PlayerModel playerModel = gameEngine.getPlayerModel();
            Direction direction = Direction.getDirectionFromKeyCode(event.getCode());
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                playerModel.move(direction);
            }
            if (direction == Direction.UP) {
                playerModel.jump();
            }

            if (event.getCode() == KeyCode.ESCAPE) {
                togglePauseMenu();
            }

            if (event.getCode() == KeyCode.F) {
                gameEngine.getCurrentGameMode().apply();
            }

            if (event.getCode() == KeyCode.G) {
                gameEngine.getCurrentGameMode().unapply();
            }
        });

        scene.setOnKeyReleased(event -> {
            PlayerModel playerModel = gameEngine.getPlayerModel();
            Direction direction = Direction.getDirectionFromKeyCode(event.getCode());
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                playerModel.stop(direction);
            }
        });
    }

    private void togglePauseMenu() {
        boolean isPaused = pauseMenuRoot.isVisible();
        pauseMenuRoot.setVisible(!isPaused);
        if (isPaused) {
            gameScene.getRoot().setEffect(null);
            gameEngine.setPause(false);
        } else {
            gameScene.getRoot().setEffect(new javafx.scene.effect.GaussianBlur(10));
            gameEngine.setPause(true);
        }
    }

    @FXML
    private void resumeGame() {
        pauseMenuRoot.setVisible(false);
        gameScene.getRoot().setEffect(null);
    }

    @FXML
    private void exitToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu/GameMenu.fxml"));
            AnchorPane mainMenuRoot = loader.load();
            GameMenuController gameMenuController = loader.getController();
            gameMenuController.setPrimaryStage(primaryStage);
            Scene mainMenuScene = new Scene(mainMenuRoot);
            primaryStage.setScene(mainMenuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

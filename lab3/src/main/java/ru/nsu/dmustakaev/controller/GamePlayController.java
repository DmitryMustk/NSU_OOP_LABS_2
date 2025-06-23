package ru.nsu.dmustakaev.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.nsu.dmustakaev.engine.GameEngine;
import ru.nsu.dmustakaev.animations.GameModeAnimation;
import ru.nsu.dmustakaev.controller.handlers.GameOverHandler;
import ru.nsu.dmustakaev.controller.handlers.InputHandler;
import ru.nsu.dmustakaev.utils.SoundEngine;
import ru.nsu.dmustakaev.view.GameObjectView;

import java.io.IOException;

public class GamePlayController {
    @FXML
    public AnchorPane endGameRoot;
    @FXML
    public Label gameModeLabel;
    @FXML
    private AnchorPane gamePlayRoot;
    @FXML
    private AnchorPane pauseMenuRoot;

    @FXML
    public ImageView winScreenPicture;
    @FXML
    public ImageView loseScreenPicture;
    @FXML
    public ImageView exitToMainMenuButton;

    private SoundEngine soundEngine;
    private GameOverHandler gameOverHandler;
    private InputHandler inputHandler;
    private GameEngine gameEngine;
    private Stage primaryStage;

    @FXML
    public void initialize() {
        soundEngine = new SoundEngine();
        soundEngine.setMusic("/game/sounds/in_game_stadium_noises.mp3");
        soundEngine.playMusic();

        gameEngine = new GameEngine(soundEngine);
        inputHandler = new InputHandler(gameEngine.getPlayerModel());

        gameOverHandler = new GameOverHandler(
                soundEngine,
                endGameRoot,
                winScreenPicture,
                loseScreenPicture,
                exitToMainMenuButton
        );

        gamePlayRoot.getChildren().addAll(gameEngine
                .getGameObjectViews()
                .stream()
                .map(GameObjectView::getPane)
                .toList()
        );
        gamePlayRoot.requestFocus();

        gameEngine.getIsFinished().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                gameOverHandler.handleGameOver(gameEngine.getWinner());
            }
        });

        gameEngine.currentGameModeProperty().addListener((observable, oldMode, newMode) -> {
            if (newMode != null) {
                showGameModeName(newMode.getTitle());
            }
        });
    }

    private void showGameModeName(String modeName) {
        GameModeAnimation.run(gameModeLabel, modeName);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setSoundEngine(SoundEngine soundEngine) {
        this.soundEngine = soundEngine;
    }

    public void setScene(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                togglePauseMenu();
                return;
            }
            inputHandler.handleKeyPressed(event.getCode());
        });

        scene.setOnKeyReleased(event -> {inputHandler.handleKeyReleased(event.getCode());});
    }

    private void togglePauseMenu() {
        boolean isPaused = pauseMenuRoot.isVisible();
        pauseMenuRoot.setVisible(!isPaused);
        if (isPaused) {
            resumeGame();
        } else {
            gameEngine.setPause(true);
            gamePlayRoot.setEffect(new GaussianBlur(10));
            pauseMenuRoot.toFront();
            pauseMenuRoot.setFocusTraversable(true);
            pauseMenuRoot.requestFocus();
        }
    }

    @FXML
    private void resumeGame() {
        pauseMenuRoot.setVisible(false);
        gameEngine.setPause(false);
        gamePlayRoot.setEffect(null);
        gamePlayRoot.requestFocus();
    }

    @FXML
    private void exitToMainMenu() {
        try {
            soundEngine.stopMusic();
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

package ru.nsu.dmustakaev.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.nsu.dmustakaev.GameEngine;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.SoundEngine;
import ru.nsu.dmustakaev.view.GameObjectView;

import java.io.IOException;

public class GamePlayController {
    @FXML
    public AnchorPane endGameRoot;
    public Label gameModeLabel;
    @FXML
    private AnchorPane gamePlayRoot;
    @FXML
    private AnchorPane pauseMenuRoot;

    @FXML
    public ImageView winScreenPicture;
    @FXML
    public ImageView loseScreenPicture;

    private SoundEngine soundEngine;
    private GameEngine gameEngine;
    private Stage primaryStage;

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

        gameEngine.getIsFinished().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                processGameOver();
            }
        });

        gameEngine.currentGameModeProperty().addListener((observable, oldMode, newMode) -> {
            if (newMode != null) {
                showGameModeName(newMode.getTitle());
            }
        });
    }

    private void showGameModeName(String modeName) {
        gameModeLabel.setText(modeName);
        gameModeLabel.setVisible(true);

        animateGameModeLable();
    }

    private void animateGameModeLable() {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), gameModeLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(0.5), gameModeLabel);
        scaleUp.setFromX(0.5);
        scaleUp.setFromY(0.5);
        scaleUp.setToX(1.5);
        scaleUp.setToY(1.5);

        ScaleTransition scaleDown = new ScaleTransition(Duration.seconds(0.5), gameModeLabel);
        scaleDown.setFromX(1.5);
        scaleDown.setFromY(1.5);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), gameModeLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(1.5));

        fadeOut.setOnFinished(event -> gameModeLabel.setVisible(false));

        SequentialTransition seqTransition = new SequentialTransition(fadeIn, scaleUp, scaleDown, fadeOut);
        seqTransition.play();

        TranslateTransition flameTransition = new TranslateTransition(Duration.seconds(0.5), gameModeLabel);
        flameTransition.setFromY(-10);
        flameTransition.setToY(10);
        flameTransition.setCycleCount(TranslateTransition.INDEFINITE);
        flameTransition.setAutoReverse(true);

        flameTransition.play();
    }


    private void processGameOver() {
        soundEngine.stopMusic();

        System.out.println(322);
        if(gameEngine.getWinner() == Direction.LEFT) {
            processWin();
            return;
        }
        processLose();
    }

    private void processWin() {
        soundEngine.setMusic("/game/sounds/game_over_music/win_music.mp3");
        soundEngine.playMusic();
        endGameRoot.toFront();
        winScreenPicture.setVisible(true);
    }

    private void processLose() {

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setSoundEngine(SoundEngine soundEngine) {
        this.soundEngine = soundEngine;
    }

    public void setScene(Scene scene) {
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

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
    @FXML
    private AnchorPane gamePlayRoot;
    @FXML
    private ImageView background_game;
    @FXML
    private ImageView field;
    @FXML
    private Label scoreLabel;

    private SoundEngine soundEngine;

    private GameEngine gameEngine;
    private Scene gameScene;
    private Stage stage;

    public GamePlayController() {}

    @FXML
    public void initialize() {
//        soundEngine.stopMusic();
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

    public void setScene(Scene scene, Stage stage) {
        this.gameScene = scene;
        this.stage = stage;

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
                showPauseMenu();
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

    private void showPauseMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu/pauseMenu.fxml"));
            AnchorPane pauseMenuRoot = loader.load();

            PauseMenuController controller = loader.getController();
            controller.setStageAndScene(stage, gameScene);

            Scene pauseMenuScene = new Scene(pauseMenuRoot);
            stage.setScene(pauseMenuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSoundEngine(SoundEngine soundEngine) {
        this.soundEngine = soundEngine;
    }
}

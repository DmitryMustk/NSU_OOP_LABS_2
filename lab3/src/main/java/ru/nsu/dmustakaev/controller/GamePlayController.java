package ru.nsu.dmustakaev.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import ru.nsu.dmustakaev.GameEngine;
import ru.nsu.dmustakaev.utils.SoundEngine;
import ru.nsu.dmustakaev.view.*;

public class GamePlayController {
    @FXML
    private AnchorPane gamePlayRoot;
    @FXML
    private ImageView background_game;
    @FXML
    private ImageView field;
    @FXML
    private Label scoreLabel;
    private final SoundEngine soundEngine;

    private GameEngine gameEngine;


    public GamePlayController(SoundEngine soundEngine) {
        this.soundEngine = soundEngine;
    }

    @FXML
    public void initialize() {
        soundEngine.stopMusic();
        soundEngine.setMusic("/game/sounds/in_game_stadium_noises.wav");
        soundEngine.playMusic();

        gameEngine = new GameEngine();

        gamePlayRoot.getChildren().addAll(gameEngine
                .getGameObjectViews()
                .stream()
                .map(GameObjectView::getPane)
                .toList()
        );
        gamePlayRoot.requestFocus();
    }

    public void setScene(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.A || code == KeyCode.D) {
                gameEngine.movePlayer(code);
            }
            if (code == KeyCode.SPACE) {
                gameEngine.jump();
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.A || code == KeyCode.D) {
                gameEngine.stopPlayer(code);
            }
        });

    }
}

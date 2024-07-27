package ru.nsu.dmustakaev.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.nsu.dmustakaev.GameEngine;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.utils.Direction;
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
            PlayerModel playerModel = gameEngine.getPlayerModel();
            Direction direction = Direction.getDirectionFromKeyCode(event.getCode());
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                playerModel.move(direction);
            }
            if (direction == Direction.UP) {
                playerModel.jump();
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
}

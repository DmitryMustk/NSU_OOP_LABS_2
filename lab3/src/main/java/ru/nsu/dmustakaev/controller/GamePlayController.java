package ru.nsu.dmustakaev.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ru.nsu.dmustakaev.GameEngine;
import ru.nsu.dmustakaev.SoundEngine;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.model.GoalModel;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.view.BackgroundView;
import ru.nsu.dmustakaev.view.BallView;
import ru.nsu.dmustakaev.view.FieldView;
import ru.nsu.dmustakaev.view.GoalView;
import ru.nsu.dmustakaev.view.PlayerView;

import static ru.nsu.dmustakaev.Main.*;

public class GamePlayController {
    @FXML
    private AnchorPane GamePlayRoot;
    @FXML
    private ImageView background_game;
    @FXML
    private ImageView field;
    private SoundEngine soundEngine;

    private GameEngine gameEngine;


    public GamePlayController(SoundEngine soundEngine) {
        this.soundEngine = soundEngine;
    }

    @FXML
    public void initialize() {
        BallModel ballModel = new BallModel();
        BallView ballView = new BallView(ballModel);

        PlayerModel playerModel = new PlayerModel();
        PlayerView playerView = new PlayerView(playerModel);

        GoalModel leftGoalModel = new GoalModel(0, SCREEN_HEIGHT - 200 - 200 + 40, 20, 160);
        GoalModel rightGoalModel = new GoalModel(SCREEN_WIDTH - 20, SCREEN_HEIGHT - 200 - 200 + 40, 20, 160);

        GoalView leftGoalView = new GoalView(leftGoalModel);
        GoalView rightGoalView = new GoalView(rightGoalModel);

        BackgroundView backgroundView = new BackgroundView();
        FieldView fieldView = new FieldView();

        soundEngine.stopMusic();
        soundEngine.setMusic("/in_game_stadium_noises.wav");
        soundEngine.playMusic();

        gameEngine = new GameEngine(ballView, ballModel, playerView, playerModel, rightGoalView);

        GamePlayRoot.getChildren().addAll(backgroundView.getPane(), fieldView.getPane(), leftGoalView.getPane(), rightGoalView.getPane(), ballView.getPane(), playerView.getPane());

        GamePlayRoot.requestFocus();
    }

    public GameEngine getGameEngine() {
        return gameEngine;
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

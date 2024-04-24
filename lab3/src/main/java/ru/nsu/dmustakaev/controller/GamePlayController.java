package ru.nsu.dmustakaev.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import ru.nsu.dmustakaev.GameEngine;
import ru.nsu.dmustakaev.utils.SoundEngine;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.model.EnemyModel;
import ru.nsu.dmustakaev.model.GoalModel;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.view.*;

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
        var ballModel = new BallModel();
        var ballView = new BallView(ballModel);

        var playerModel = new PlayerModel();
        var playerView = new PlayerView(playerModel);

        var leftGoalModel = new GoalModel(0, 240, 160, 20);
        var rightGoalModel = new GoalModel(SCREEN_WIDTH - 20, SCREEN_HEIGHT - 200 - 200 + 40, 160, 20);

        var leftGoalView = new GoalView(leftGoalModel, Direction.LEFT);
        var rightGoalView = new GoalView(rightGoalModel, Direction.RIGHT);

        var backgroundView = new BackgroundView();
        var fieldView = new FieldView();

        var enemyModel = new EnemyModel(ballModel, leftGoalModel, rightGoalModel);
        var enemyView = new EnemyView(enemyModel);

        soundEngine.stopMusic();
        soundEngine.setMusic("/in_game_stadium_noises.wav");
        soundEngine.playMusic();

        gameEngine = new GameEngine(ballView, ballModel, playerView, playerModel, rightGoalView, enemyModel, enemyView, leftGoalView);

        GamePlayRoot.getChildren().addAll(backgroundView.getPane(), fieldView.getPane(), leftGoalView.getPane(), rightGoalView.getPane(), ballView.getPane(), playerView.getPane(), enemyView.getPane());

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

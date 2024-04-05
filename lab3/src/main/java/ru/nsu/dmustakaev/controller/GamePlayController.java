package ru.nsu.dmustakaev.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.model.GoalModel;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.view.BackgroundView;
import ru.nsu.dmustakaev.controller.BallController;
import ru.nsu.dmustakaev.view.BallView;
import ru.nsu.dmustakaev.view.FieldView;
import ru.nsu.dmustakaev.view.GoalView;
import ru.nsu.dmustakaev.view.PlayerView;

import static ru.nsu.dmustakaev.Main.addMusic;
import static ru.nsu.dmustakaev.Main.mediaPlayer;

public class GamePlayController {
    @FXML
    private AnchorPane GamePlayRoot;
    @FXML
    private ImageView background_game;
    @FXML
    private ImageView field;

    private BallModel ballModel;
    private BallView ballView;

    private PlayerModel playerModel;
    private PlayerView playerView;

    private GoalView leftGoalView;
    private GoalView rightGoalView;

    private BallController ballController;

    @FXML
    public void initialize() {
        mediaPlayer.stop();
        Media sound = new Media(getClass().getResource("/in_game_stadium_noises.mp3").toString());
        mediaPlayer = new MediaPlayer(sound);
        addMusic();
        ballModel = new BallModel();
        ballView = new BallView(ballModel);

        playerModel = new PlayerModel();
        playerView = new PlayerView(playerModel);

        GoalModel leftGoalModel = new GoalModel(0, 400 - 200 + 40, 20, 160);
        GoalModel rightGoalModel = new GoalModel(1004, 400 - 200 + 40, 20, 160);

        leftGoalView = new GoalView(leftGoalModel);
        rightGoalView = new GoalView(rightGoalModel);

        BackgroundView backgroundView = new BackgroundView();
        FieldView fieldView = new FieldView();

        ballController = new BallController(ballView, ballModel, playerView, playerModel);

        GamePlayRoot.getChildren().addAll(backgroundView.getPane(), fieldView.getPane(), leftGoalView.getPane(), rightGoalView.getPane(), ballView.getPane(), playerView.getPane());

        GamePlayRoot.requestFocus();
    }

    public void setScene(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.A || code == KeyCode.D) {
                ballController.movePlayer(code);
            }
            if (code == KeyCode.SPACE) {
                ballController.jump();
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.A || code == KeyCode.D) {
                ballController.stopPlayer(code);
            }
        });
    }
}

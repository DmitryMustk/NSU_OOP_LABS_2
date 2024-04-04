package ru.nsu.dmustakaev;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import ru.nsu.dmustakaev.controller.BallController;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.model.GoalModel;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.view.*;

public class Main extends Application {

    private BallModel ballModel;
    private BallView ballView;

    private PlayerModel playerModel;
    private PlayerView playerView;

    private GoalView leftGoalView;
    private GoalView rightGoalView;

    private BallController ballController;

    @Override
    public void start(Stage primaryStage) {
        ballModel = new BallModel();
        ballView = new BallView(ballModel);

        playerModel = new PlayerModel();
        playerView = new PlayerView(playerModel);

        GoalModel leftGoalModel = new GoalModel(0, 400 - 200 + 40, 20, 160);
        GoalModel rightGoalModel = new GoalModel(1004, 400-200 + 40, 20, 160);

        leftGoalView = new GoalView(leftGoalModel);
        rightGoalView = new GoalView(rightGoalModel);

        BackgroundView backgroundView = new BackgroundView();
        FieldView fieldView = new FieldView();

        ballController = new BallController(ballView, ballModel, playerView, playerModel);


        Group root = new Group();
        root.getChildren().addAll(backgroundView.getPane(), fieldView.getPane(), leftGoalView.getPane(), rightGoalView.getPane(), ballView.getPane(), playerView.getPane());

        Scene scene = new Scene(root, 1024, 768);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if(code == KeyCode.A || code == KeyCode.D) {
                ballController.movePlayer(code);
            }
            if(code == KeyCode.SPACE) {
                ballController.jump();
            }
        });
        scene.setOnKeyReleased(event -> {
            KeyCode code = event.getCode();
            if(code == KeyCode.A || code == KeyCode.D) {
                ballController.stopPlayer(code);
            }
        });
        primaryStage.setTitle("HeadBall");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





package ru.nsu.dmustakaev;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import ru.nsu.dmustakaev.controller.BallController;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.view.BallView;
import ru.nsu.dmustakaev.view.PlayerView;

public class Main extends Application {

    private BallModel ballModel;
    private BallView ballView;

    private PlayerModel playerModel;
    private PlayerView playerView;

    private BallController ballController;

    @Override
    public void start(Stage primaryStage) {
        ballModel = new BallModel();
        ballView = new BallView(ballModel);

        playerModel = new PlayerModel();
        playerView = new PlayerView(playerModel);

        ballController = new BallController(ballView, ballModel, playerView, playerModel);


        Group root = new Group();
        root.getChildren().addAll(ballView.getPane(), playerView.getPane());

        Scene scene = new Scene(root, 1024, 768);

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if(code == KeyCode.LEFT || code == KeyCode.RIGHT) {
                ballController.movePlayer(code);
            }
            if(code == KeyCode.SPACE) {
                ballController.jump();
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





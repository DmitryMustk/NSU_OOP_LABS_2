package ru.nsu.dmustakaev;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import ru.nsu.dmustakaev.controller.BallController;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.view.BallView;

public class Main extends Application {

    private BallModel ballModel;
    private BallView ballView;
    private BallController ballController;

    @Override
    public void start(Stage primaryStage) {
        ballModel = new BallModel();
        ballView = new BallView(ballModel);
        ballController = new BallController(ballView, ballModel);


        Group root = new Group();
        root.getChildren().addAll(ballView.getPane());

        Scene scene = new Scene(root, 1024, 768);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                ballController.hitBall();
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





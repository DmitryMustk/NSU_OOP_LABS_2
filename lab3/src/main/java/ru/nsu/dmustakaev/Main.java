package ru.nsu.dmustakaev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javafx.stage.Stage;

import ru.nsu.dmustakaev.controller.BallController;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.view.*;


public class Main extends Application {
    public static MediaPlayer mediaPlayer;

    private BallModel ballModel;
    private BallView ballView;

    private PlayerModel playerModel;
    private PlayerView playerView;

    private GoalView leftGoalView;
    private GoalView rightGoalView;

    private BallController ballController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent mainPage = FXMLLoader.load(getClass().getResource("/GameMenu.fxml"));
        Scene scene = new Scene(mainPage, 1024, 600);
        primaryStage.setTitle("HeadBall");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void addMusic() {
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(80));
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





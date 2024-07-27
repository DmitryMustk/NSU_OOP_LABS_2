package ru.nsu.dmustakaev;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ru.nsu.dmustakaev.model.*;
import ru.nsu.dmustakaev.utils.Bounds;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.SoundEngine;
import ru.nsu.dmustakaev.view.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static ru.nsu.dmustakaev.Main.SCREEN_HEIGHT;
import static ru.nsu.dmustakaev.Main.SCREEN_WIDTH;

public class GameEngine {
    private static final int FPS = 240;

    private BallModel ballModel;
    private PlayerModel playerModel;
    private EnemyModel enemyModel;
    private GoalModel leftGoalModel;
    private GoalModel rightGoalModel;
    private ScoreModel scoreModel;

    private List<GameObjectView> staticGameObjectViews;
    private List<DynamicGameObjectView> dynamicGameObjectViews;

    private final SoundEngine soundEngine;

    private void createGameObjectsModels() {
        leftGoalModel = new GoalModel(Direction.LEFT,0,  240, 160, 20);
        rightGoalModel = new GoalModel(Direction.RIGHT,SCREEN_WIDTH - 20, SCREEN_HEIGHT - 360, 160, 20);

        ballModel = new BallModel();

        playerModel = new PlayerModel();
        enemyModel = new EnemyModel(ballModel, leftGoalModel, rightGoalModel);
        scoreModel = new ScoreModel();
    }

    private void createGameObjectViews() {
        staticGameObjectViews = new ArrayList<>();
        staticGameObjectViews.addAll(Arrays.asList(
                new BackgroundView(),
                new FieldView(),
                new GoalView(leftGoalModel),
                new GoalView(rightGoalModel)
        ));

        dynamicGameObjectViews = new ArrayList<>();
        dynamicGameObjectViews.addAll(Arrays.asList(
                new BallView(ballModel),
                new PlayerView(playerModel),
                new EnemyView(enemyModel),
                new ScoreView(scoreModel)
        ));
    }


    public GameEngine(SoundEngine soundEngine) {
        createGameObjectsModels();
        createGameObjectViews();

        this.soundEngine = soundEngine;

//        soundEngine = new SoundEngine();

        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / FPS), actionEvent -> {
            dynamicGameObjectViews.forEach(DynamicGameObjectView::update);
            checkCollision();
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public List<GameObjectView> getGameObjectViews() {
        return Stream.concat(
                staticGameObjectViews.stream(),
                dynamicGameObjectViews.stream()
        ).toList();
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    private void checkCollision() {
        Bounds ballBounds = ballModel.getBounds();
        Bounds playerBounds = playerModel.getBounds();
        Bounds enemyBounds = enemyModel.getBounds();
        Bounds leftGoalBounds = leftGoalModel.getBounds();
        Bounds rightGoalBounds = rightGoalModel.getBounds();

        if (ballBounds.intersects(playerBounds)) {
            ballModel.kick(playerBounds);
        }
        if (ballBounds.intersects(enemyBounds)) {
            ballModel.kick(enemyBounds);
        }

        if (leftGoalBounds.intersects(ballBounds)) {
            resetAfterScore();
            soundEngine.playSound("/game/sounds/score/fail.mp3");
            scoreModel.incrementEnemyScore();
        }

        if (rightGoalBounds.intersects(ballBounds)) {
            resetAfterScore();
            soundEngine.playSound("/game/sounds/score/sii.mp3");
            scoreModel.incrementPlayerScore();
        }

        if (playerBounds.intersects(enemyBounds)) {
            Direction playerPushDirection = playerModel.getX() < enemyModel.getX() ? Direction.LEFT : Direction.RIGHT;
            Direction enemyPushDirection = playerPushDirection == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
            playerModel.pushBack(playerPushDirection);
            enemyModel.pushBack(enemyPushDirection);
        }
    }

    private void resetAfterScore() {
        ballModel.reset();
        playerModel.reset();
        enemyModel.reset();
    }

}

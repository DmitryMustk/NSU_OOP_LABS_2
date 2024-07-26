package ru.nsu.dmustakaev;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.model.EnemyModel;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.utils.SoundEngine;
import ru.nsu.dmustakaev.view.*;

public class GameEngine {
    private final BallView ballView;
    private final BallModel ballModel;

    private final PlayerView playerView;
    private final PlayerModel playerModel;

    private final EnemyView enemyView;
    private final EnemyModel enemyModel;

    private final GoalView leftGoalView;
    private final GoalView rightGoalView;
    private final ScoreView scoreView;

    private int playerScore;
    private int enemyScore;

    private static final int FPS = 240;

    private final SoundEngine soundEngine;

//    private final List<UpdatableModel> updatableModelList;
//    private final List<GameObjectView> gameObjectViewList;

    private Timeline timeline = new Timeline();

    public GameEngine(BallView ballView, BallModel ballModel, PlayerView playerView, PlayerModel playerModel, GoalView rightGoalView, EnemyModel enemyModel, EnemyView enemyView, GoalView leftGoalView, ScoreView scoreView) {
        this.ballView = ballView;
        this.ballModel = ballModel;
        this.playerView = playerView;
        this.playerModel = playerModel;
        this.enemyView = enemyView;
        this.enemyModel = enemyModel;

        this.leftGoalView = leftGoalView;
        this.rightGoalView = rightGoalView;

        this.scoreView = scoreView;

        soundEngine = new SoundEngine();

        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / FPS), actionEvent -> {
            playerView.update();
            enemyView.update();
            ballView.update();
            checkCollision();
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public SoundEngine getSoundEngine() {
        return soundEngine;
    }

    public void movePlayer(KeyCode keyCode) {
        Direction direction = keyCode == KeyCode.A ? Direction.LEFT : Direction.RIGHT;
        playerModel.move(direction);
    }

    public void stopPlayer(KeyCode keyCode) {
        Direction direction = keyCode == KeyCode.A ? Direction.LEFT : Direction.RIGHT;
        playerModel.stop(direction);
    }

    public void jump() {
        playerModel.jump();
    }

    private void resetAfterScore() {
        scoreView.reset(playerScore, enemyScore);
        ballModel.reset();
        playerModel.reset();
        enemyModel.reset();
    }

    private void checkCollision() {
        var ballBounds = ballView.getBounds();
        var playerBounds = playerView.getBounds();
        var enemyBounds = enemyView.getBounds();
        var leftGoalBounds = leftGoalView.getBounds();
        var rightGoalBounds = rightGoalView.getBounds();

        if (ballBounds.intersects(playerBounds)) {
            ballModel.kick(playerBounds);
        }
        if (ballBounds.intersects(enemyBounds)) {
            ballModel.kick(enemyBounds);
        }

        if (leftGoalBounds.intersects(ballBounds)) {
            soundEngine.playSound("/game/sounds/score/fail.mp3");
            enemyScore++;
            resetAfterScore();
        }

        if (rightGoalBounds.intersects(ballBounds)) {
            soundEngine.playSound("/game/sounds/score/sii.mp3");
            playerScore++;
            resetAfterScore();
        }

        if (playerBounds.intersects(enemyBounds)) {
            Direction playerPushDirection = playerModel.getX() < enemyModel.getX() ? Direction.LEFT : Direction.RIGHT;
            Direction enemyPushDirection = playerPushDirection == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
            playerModel.pushBack(playerPushDirection);
            enemyModel.pushBack(enemyPushDirection);
        }
    }
}

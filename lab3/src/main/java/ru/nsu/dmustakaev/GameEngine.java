package ru.nsu.dmustakaev;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.model.EnemyModel;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.utils.SoundEngine;
import ru.nsu.dmustakaev.view.BallView;
import ru.nsu.dmustakaev.view.EnemyView;
import ru.nsu.dmustakaev.view.GoalView;
import ru.nsu.dmustakaev.view.PlayerView;

import java.util.Random;

public class GameEngine {
    private final BallView ballView;
    private final BallModel ballModel;

    private final PlayerView playerView;
    private final PlayerModel playerModel;

    private final EnemyView enemyView;
    private final EnemyModel enemyModel;

    private final GoalView leftGoalView;
    private final GoalView rightGoalView;
    private Random random;
    private SoundEngine soundEngine;

    private long lastUpdateTime = 0;
    private long updateInterval = 1_000_00000L / 30; // 30 FPS

    private static final String[] bumpSounds = {"/bump_sound_1.mp3", "/bump_sound_2.mp3"};
    private static final String[] kickBallSounds = {"/ball_kick_sound_1.mp3", "/ball_kick_sound_2.mp3", "/ball_kick_sound_3.wav", "/ball_kick_sound_4.wav"};
//    private final List<UpdatableModel> updatableModelList;
//    private final List<GameObjectView> gameObjectViewList;

    private Timeline timeline = new Timeline();

    public GameEngine(BallView ballView, BallModel ballModel, PlayerView playerView, PlayerModel playerModel, GoalView rightGoalView, EnemyModel enemyModel, EnemyView enemyView, GoalView leftGoalView) {
        this.ballView = ballView;
        this.ballModel = ballModel;
        this.playerView = playerView;
        this.playerModel = playerModel;
        this.enemyView = enemyView;
        this.enemyModel = enemyModel;

        this.leftGoalView = leftGoalView;
        this.rightGoalView = rightGoalView;

        random = new Random();
        soundEngine = new SoundEngine();

        KeyFrame frame = new KeyFrame(Duration.seconds(0.008), actionEvent -> {
            playerView.update();
            enemyView.update();
            ballView.update();
            checkCollision();
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
//        AnimationTimer timer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                try {
//                    Thread.sleep(1000 / 100);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                playerView.update();
//                enemyView.update();
//                ballView.update();
//                checkCollision();
//
//            }
//        };
//
//        timer.start();
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

        double ballCenterX = ballBounds.getCenterX();
        double playerCenterX = playerBounds.getCenterX();
        double enemyCenterX = enemyBounds.getCenterX();

        Direction playerKickDirection = ballCenterX > playerCenterX ? Direction.RIGHT : Direction.LEFT;
        Direction enemyKickDirection = ballCenterX > enemyCenterX ? Direction.RIGHT : Direction.LEFT;

        if (ballBounds.intersects(playerBounds)) {
            ballModel.kick(playerKickDirection);
//            soundEngine.playSound(kickBallSounds[random.nextInt(kickBallSounds.length)]);
        }
        if (ballBounds.intersects(enemyBounds)) {
            ballModel.kick(enemyKickDirection);
//            soundEngine.playSound(kickBallSounds[random.nextInt(kickBallSounds.length)]);
        }

        if (leftGoalBounds.intersects(ballBounds)) {
            soundEngine.playSound("/fail.mp3");
            resetAfterScore();
        }

        if (rightGoalBounds.intersects(ballBounds)) {
            soundEngine.playSound("/sii.mp3");
            resetAfterScore();
        }

        if (playerBounds.intersects(enemyBounds)) {
            Direction playerPushDirection = playerModel.getX() < enemyModel.getX() ? Direction.LEFT : Direction.RIGHT;
            Direction enemyPushDirection = playerPushDirection == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
            playerModel.pushBack(playerPushDirection);
            enemyModel.pushBack(enemyPushDirection);
            soundEngine.playSound(bumpSounds[random.nextInt(bumpSounds.length)]);
        }
    }
}

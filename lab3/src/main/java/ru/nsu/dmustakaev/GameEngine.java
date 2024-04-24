package ru.nsu.dmustakaev;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.model.EnemyModel;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.utils.SoundEngine;
import ru.nsu.dmustakaev.view.BallView;
import ru.nsu.dmustakaev.view.EnemyView;
import ru.nsu.dmustakaev.view.GoalView;
import ru.nsu.dmustakaev.view.PlayerView;


public class GameEngine {
    private final BallView ballView;
    private final BallModel ballModel;

    private final PlayerView playerView;
    private final PlayerModel playerModel;

    private final EnemyView enemyView;
    private final EnemyModel enemyModel;

    private final GoalView leftGoalView;
    private final GoalView rightGoalView;
    private SoundEngine soundEngine;
//    private final List<UpdatableModel> updatableModelList;
//    private final List<GameObjectView> gameObjectViewList;


    public GameEngine(BallView ballView, BallModel ballModel, PlayerView playerView, PlayerModel playerModel, GoalView rightGoalView, EnemyModel enemyModel, EnemyView enemyView, GoalView leftGoalView) {
        this.ballView = ballView;
        this.ballModel = ballModel;
        this.playerView = playerView;
        this.playerModel = playerModel;
        this.enemyView = enemyView;
        this.enemyModel = enemyModel;

        this.leftGoalView = leftGoalView;
        this.rightGoalView = rightGoalView;

        soundEngine = new SoundEngine();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                playerView.update();
                enemyView.update();
                ballView.update();
                checkCollision();
            }
        };

        timer.start();
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
        }
        if (ballBounds.intersects(enemyBounds)) {
            ballModel.kick(enemyKickDirection);
        }

        if(leftGoalBounds.intersects(ballBounds)) {
            soundEngine.playSound("/fail.mp3");
            ballModel.reset();
        }

        if (rightGoalBounds.intersects(ballBounds)) {
            soundEngine.playSound("/sii.mp3");
            ballModel.reset();
        }

        if (playerBounds.intersects(enemyBounds)) {
            Direction playerPushDirection = playerModel.getX() < enemyModel.getX() ? Direction.LEFT : Direction.RIGHT;
            Direction enemyPushDirection = playerPushDirection == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
            playerModel.pushBack(playerPushDirection);
            enemyModel.pushBack(enemyPushDirection);
        }
    }
}

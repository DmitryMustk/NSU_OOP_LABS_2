package ru.nsu.dmustakaev;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.view.BallView;
import ru.nsu.dmustakaev.view.GoalView;
import ru.nsu.dmustakaev.view.PlayerView;

import java.util.ArrayList;
import java.util.List;


public class GameEngine {
    private final BallView ballView;
    private final BallModel ballModel;

    private final PlayerView playerView;
    private final PlayerModel playerModel;

    private final GoalView rightGoalView;
    private SoundEngine soundEngine;
//    private final List<UpdatableModel> updatableModelList;
//    private final List<GameObjectView> gameObjectViewList;


    public GameEngine(BallView ballView, BallModel ballModel, PlayerView playerView, PlayerModel playerModel, GoalView rightGoalView) {
        this.ballView = ballView;
        this.ballModel = ballModel;
        this.playerView = playerView;
        this.playerModel = playerModel;

        this.rightGoalView = rightGoalView;

        soundEngine = new SoundEngine();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                playerView.update();
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
        var rightGoalBounds = rightGoalView.getBounds();

        double ballCenterX = ballBounds.getCenterX();
        double playerCenterX = playerBounds.getCenterX();

        Direction direction = ballCenterX > playerCenterX ? Direction.RIGHT : Direction.LEFT;

        if (ballBounds.intersects(playerBounds)) {
            ballModel.kick(direction);
        }

        if (rightGoalBounds.intersects(ballBounds)) {
            soundEngine.playSound("/sii.mp3");
            ballModel.reset();
        }
    }
}

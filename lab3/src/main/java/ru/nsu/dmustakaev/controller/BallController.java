package ru.nsu.dmustakaev.controller;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.view.BallView;
import ru.nsu.dmustakaev.view.PlayerView;

public class BallController {
    private BallView ballView;
    private BallModel ballModel;

    private PlayerView playerView;
    private PlayerModel playerModel;


    public BallController(BallView ballView, BallModel ballModel, PlayerView playerView, PlayerModel playerModel) {
        this.ballView = ballView;
        this.ballModel = ballModel;

        this.playerView = playerView;
        this.playerModel = playerModel;



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
        Bounds ballBounds = ballView.getBounds();
        Bounds playerBounds = playerView.getBounds();

        double ballCenterX = ballBounds.getCenterX();
        double playerCenterX = playerBounds.getCenterX();

        Direction direction = ballCenterX > playerCenterX ? Direction.RIGHT : Direction.LEFT;

        if (ballBounds.intersects(playerBounds)) {
            ballModel.kick(direction);
        }
    }
}

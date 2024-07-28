package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.BallModel;

public class BigBallGameMode implements GameMode {
    private BallModel ballModel;

    private final static int BALL_RADIUS_MULTIPLIER = 2;

    public BigBallGameMode(BallModel ballModel) {
        this.ballModel = ballModel;
    }

    @Override
    public void apply() {
        ballModel.setRadius(ballModel.getRadius() * BALL_RADIUS_MULTIPLIER);
    }

    @Override
    public void unapply() {
        ballModel.setRadius(ballModel.getRadius() / BALL_RADIUS_MULTIPLIER);
    }
}

package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.BallModel;

public class SmallBallGameMode implements GameMode {
    private BallModel ballModel;

    private final static double BALL_RADIUS_MULTIPLIER = 0.5;

    public SmallBallGameMode(BallModel ballModel) {
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

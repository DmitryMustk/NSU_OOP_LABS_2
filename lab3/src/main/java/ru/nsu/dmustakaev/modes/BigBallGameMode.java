package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.BallModel;

public class BigBallGameMode implements GameMode {
    private final BallModel ballModel;

    private static final String SOUND_SOURCE = "/game/sounds/modes/big_ball.mp3";

    private final static int BALL_RADIUS_MULTIPLIER = 2;

    public BigBallGameMode(BallModel ballModel) {
        this.ballModel = ballModel;
    }

    @Override
    public String getSoundSource() {
        return SOUND_SOURCE;
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

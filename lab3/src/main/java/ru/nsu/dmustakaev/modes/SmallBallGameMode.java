package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.BallModel;

public class SmallBallGameMode implements GameMode {
    private final BallModel ballModel;

    private final static double BALL_RADIUS_MULTIPLIER = 0.5;

    private static final String SOUND_SOURCE = "/game/sounds/modes/small_ball.mp3";

    public SmallBallGameMode(BallModel ballModel) {
        this.ballModel = ballModel;
    }

    @Override
    public String getSoundSource() {
        return SOUND_SOURCE;
    }

    @Override
    public String getTitle() {
        return "Small Ball";
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

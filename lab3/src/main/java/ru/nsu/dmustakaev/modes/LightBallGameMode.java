package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.BallModel;

public class LightBallGameMode implements GameMode {
    private BallModel ballModel;
    private double defaultBounceFactor;

    private static final double LIGHT_BOUNCE_FACTOR = 1.0;

    public LightBallGameMode(BallModel ballModel) {
        this.ballModel = ballModel;
    }

    @Override
    public void apply() {
        defaultBounceFactor = ballModel.getBounceFactor();
        ballModel.setBounceFactor(LIGHT_BOUNCE_FACTOR);
    }

    @Override
    public void unapply() {
        ballModel.setBounceFactor(defaultBounceFactor);
    }
}

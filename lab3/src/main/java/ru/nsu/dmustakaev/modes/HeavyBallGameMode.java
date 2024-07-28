package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.BallModel;

public class HeavyBallGameMode implements GameMode {
    private BallModel ballModel;
    private double defaultBounceFactor;

    private static final double HEAVY_BOUNCE_FACTOR = 0.4;

    public HeavyBallGameMode(BallModel ballModel) {
        this.ballModel = ballModel;
    }

    @Override
    public void apply() {
        defaultBounceFactor = ballModel.getBounceFactor();
        ballModel.setBounceFactor(HEAVY_BOUNCE_FACTOR);
    }

    @Override
    public void unapply() {
        ballModel.setBounceFactor(defaultBounceFactor);
    }
}

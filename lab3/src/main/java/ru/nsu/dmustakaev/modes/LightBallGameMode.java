package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.BallModel;

public class LightBallGameMode implements GameMode {
    private BallModel ballModel;
    private double defaultBounceFactor;

    private static final double LIGHT_BOUNCE_FACTOR = 1.0;

    private static final String SOUND_SOURCE = "/game/sounds/modes/light_ball.mp3";

    public LightBallGameMode(BallModel ballModel) {
        this.ballModel = ballModel;
    }

    @Override
    public String getSoundSource() {
        return SOUND_SOURCE;
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

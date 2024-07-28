package ru.nsu.dmustakaev.modes;

public class DefaultGameMode implements GameMode {
    private static final String SOUND_SOURCE = "/game/sounds/modes/default.mp3";

    @Override
    public String getSoundSource() {
        return SOUND_SOURCE;
    }

    @Override
    public void apply() {

    }

    @Override
    public void unapply() {

    }
}

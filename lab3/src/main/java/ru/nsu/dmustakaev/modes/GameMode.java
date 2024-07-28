package ru.nsu.dmustakaev.modes;

public interface GameMode {
    String getSoundSource();

    void apply();
    void unapply();
}

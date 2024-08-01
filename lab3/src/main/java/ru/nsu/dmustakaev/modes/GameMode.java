package ru.nsu.dmustakaev.modes;

public interface GameMode {
    String getSoundSource();
    String getTitle();

    void apply();
    void unapply();
}

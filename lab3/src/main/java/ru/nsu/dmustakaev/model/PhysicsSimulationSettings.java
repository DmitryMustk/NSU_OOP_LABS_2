package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.Main;

public record PhysicsSimulationSettings(
        double gravity,
        double airResistance,
        double solidResistance
) {
    public static final int FLOOR = Main.SCREEN_HEIGHT * 2 / 3;
}

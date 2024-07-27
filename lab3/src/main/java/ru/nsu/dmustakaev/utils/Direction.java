package ru.nsu.dmustakaev.utils;

import javafx.scene.input.KeyCode;

public enum Direction {
    LEFT,
    RIGHT,
    DOWN,
    UP,
    NONE,
    ;

    public static Direction getDirectionFromKeyCode(KeyCode keyCode) {
        return switch (keyCode) {
            case KeyCode.A -> LEFT;
            case KeyCode.D -> RIGHT;
            case KeyCode.S -> DOWN;
            case KeyCode.SPACE -> UP;
            default -> NONE;
        };
    }
}

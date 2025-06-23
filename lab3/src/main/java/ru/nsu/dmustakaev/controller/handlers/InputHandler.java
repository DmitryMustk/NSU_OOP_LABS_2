package ru.nsu.dmustakaev.controller.handlers;

import javafx.scene.input.KeyCode;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.utils.Direction;


public class InputHandler {
    private final PlayerModel playerModel;

    public InputHandler(PlayerModel playerModel) {
        this.playerModel = playerModel;
    }

    public void handleKeyPressed(KeyCode keyCode) {
        Direction direction = Direction.fromKeyCode(keyCode);
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            playerModel.move(direction);
        } else if (direction == Direction.UP) {
            playerModel.jump();
        }
    }

    public void handleKeyReleased(KeyCode keyCode) {
        Direction direction = Direction.fromKeyCode(keyCode);
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            playerModel.stop(direction);
        }
    }
}

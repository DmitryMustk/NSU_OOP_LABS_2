package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.utils.Bounds;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.Vector2D;

public class GoalModel {
    private final Vector2D cords;
    private Direction direction;
    private final int height;
    private final int width;

    public GoalModel(Direction direction, double x, double y, int height, int width) {
        this.direction = direction;
        this.cords = new Vector2D(x, y);
        this.height = height;
        this.width = width;
    }

    public double getX() {
        return cords.getX();
    }

    public double getY() {
        return cords.getY();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Bounds getBounds() {
        return new Bounds(getX(), getY(), getHeight(), getWidth());
    }

    public Direction getDirection() {
        return direction;
    }
}

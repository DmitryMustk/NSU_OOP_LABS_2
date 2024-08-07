package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.Main;
import ru.nsu.dmustakaev.utils.Bounds;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.Vector2D;

public class GoalModel {
    private final Vector2D cords;
    private final Direction direction;

    private int height;
    private int width;

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

    public void setY(double y) {
        this.cords.setY(y);
    }


    public Bounds getBounds() {
        return new Bounds(getX(), getY(), height, width);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height < 0) {
            throw new IllegalArgumentException("Height cannot be negative");
        }
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public Direction getDirection() {
        return direction;
    }
}

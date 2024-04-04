package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.utils.Vector2D;

public class GoalModel {
    private Vector2D cords;
    private int height;
    private int width;

    public GoalModel(double x, double y, int height, int width) {
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
}

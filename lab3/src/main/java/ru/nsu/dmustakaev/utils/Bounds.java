package ru.nsu.dmustakaev.utils;

public class Bounds {
    private final double minX;
    private final double minY;
    private final double height;
    private final double width;

    public Bounds(double minX, double minY, double height, double width) {
        this.minX = minX;
        this.minY = minY;
        this.height = height;
        this.width = width;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getCenterX() {
        return minX + width / 2.0;
    }

    public double getCenterY() {
        return minY - height / 2.0;
    }

    public boolean intersects(Bounds other) {
        boolean intersectsX = minX + width >= other.getMinX() && other.getMinX() + other.getWidth() >= minX;
        boolean intersectsY = minY + height >= other.getMinY() && other.getMinY() + other.getHeight() >= minY;

        return intersectsX && intersectsY;
    }
}

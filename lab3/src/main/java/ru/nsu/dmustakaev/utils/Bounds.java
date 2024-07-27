package ru.nsu.dmustakaev.utils;

public record Bounds(double minX, double minY, double height, double width) {

    public double getCenterX() {
        return minX + width / 2.0;
    }

    public double getCenterY() {
        return minY - height / 2.0;
    }

    public boolean intersects(Bounds other) {
        boolean intersectsX = minX + width >= other.minX() && other.minX() + other.width() >= minX;
        boolean intersectsY = minY + height >= other.minY() && other.minY() + other.height() >= minY;

        return intersectsX && intersectsY;
    }
}

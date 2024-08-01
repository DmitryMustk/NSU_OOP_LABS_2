package ru.nsu.dmustakaev.utils;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public static Vector2D getZeroVector() {
        return new Vector2D(0, 0);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void copyVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void copyVector(Vector2D vector) {
        this.x = vector.getX();
        this.y = vector.getY();
    }

    public void addVector(Vector2D vec) {
        this.x += vec.x;
        this.y += vec.y;
    }

    public void addVector(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void mulVecOnScalar(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public double getLength() {
        return Math.sqrt(x * x + y + y);
    }

}

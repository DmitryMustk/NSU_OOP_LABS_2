package ru.nsu.dmustakaev.utils;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2D() {
        this.x = 0;
        this.y = 0;
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

    public void copyVector(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
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

    public void normalize() {
        this.mulVecOnScalar(1 / this.getLength());
    }
}

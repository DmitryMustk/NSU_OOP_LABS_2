package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.utils.Direction;

public class BallModel implements UpdatableModel {
    private double X = 100;
    private double Y = 100;
    private double SpeedX = 0;
    private double SpeedY = 0;

    private static final double RADIUS = 12;
    private static final double GRAVITY = 0.01;
    private static final double AIR_RESISTANCE = 0.0005;
    private static final double SOLD_RESISTANCE = 0.02;
    private static final double BOUNCE_FACTOR = 0.8;

    private static final double MAX_SPEED_X = 3;
    private static final double MAX_SPEED_Y = 3;

    private static final int FLOOR = 400;

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getBallRadius() {
        return RADIUS;
    }

    public double getCentreX() {
        return X - RADIUS;
    }

    public double getCentreY() {
        return Y - RADIUS;
    }

    public boolean isMove() {
        return Math.abs(SpeedX) + Math.abs(SpeedY) > 0.2;
    }

    public void kick(Direction direction) {
        SpeedX += direction == Direction.RIGHT ? 1: -1;
        SpeedY -= 1.5;
    }

    private void checkBounds() {
        if (Y + RADIUS >= FLOOR) {
            Y = FLOOR - RADIUS;
            SpeedY = -SpeedY * BOUNCE_FACTOR;
        }

        if (X - RADIUS <= 0) {
            X = RADIUS;
            SpeedX = -SpeedX * BOUNCE_FACTOR;
        }

        if (X + RADIUS >= 1024) {
            X = 1024 - RADIUS;
            SpeedX = -SpeedX * BOUNCE_FACTOR;
        }

        if (Y - RADIUS <= 0) {
            Y = 0 + RADIUS;
            SpeedY = -SpeedY * BOUNCE_FACTOR;
        }
    }
    private void calculateGravity() {
        if (Y + RADIUS < FLOOR) {
            SpeedY += GRAVITY;
        }
    }

    private void calculateAirResistance() {
        if (SpeedX != 0) {
            SpeedX = (SpeedX / Math.abs(SpeedX)) * (Math.abs(SpeedX) - AIR_RESISTANCE);
        }
    }

    private void calculateFrictionForce() {
        if (SpeedX != 0 && Y + RADIUS > FLOOR - 3) {
            SpeedX = (SpeedX / Math.abs(SpeedX)) * (Math.abs(SpeedX) - SOLD_RESISTANCE);
        }
    }
    private void calculateTotalAcceleration() {
        calculateGravity();
        calculateFrictionForce();
        calculateAirResistance();
    }

    @Override
    public void update() {
        calculateTotalAcceleration();
        System.out.println("x: " + X + " y: " + Y);
        checkBounds();

        X += SpeedX;
        Y += SpeedY;

        SpeedX = Math.min(SpeedX, MAX_SPEED_X);
        SpeedY = Math.min(SpeedY, MAX_SPEED_Y);
    }

}

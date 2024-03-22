package ru.nsu.dmustakaev.model;

public class PlayerModel implements UpdatableModel {
    private double X = 100;
    private double Y = 100;
    private double SpeedX = 0;
    private double SpeedY = 0;

    private static final double RADIUS = 36;
    private static final double GRAVITY = 0.01;
    private static final double AIR_RESISTANCE = 0.03;
    private static final double FRICTION = 0.05;
    private static final double BOUNCE_FACTOR = 0.1;
    private static final double JUMP_SPEED = 15;
    private static final double MAX_SPEED_X = 3;
    private static final double MAX_SPEED_Y = 15;

    private static final int FLOOR_DELTA = 3;
    private static final int FLOOR = 400;

    public double getRadius() {
        return RADIUS;
    }

    public double getCentreX() {
        return X - RADIUS;
    }

    public double getCentreY() {
        return Y - RADIUS;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getSpeedX() {
        return SpeedX;
    }

    public double getSpeedY() {
        return SpeedY;
    }

    private boolean isOnGround() {
        return Y + RADIUS >= FLOOR - FLOOR_DELTA;
    }

    public void jump() {
        if (isOnGround()) {
            SpeedY = JUMP_SPEED;
        }
    }

    public void move(Direction direction) {
        double accelerationX = direction == Direction.RIGHT ? 1 : -1;
        SpeedX += accelerationX;
        SpeedX = Math.min(MAX_SPEED_X, Math.max(-MAX_SPEED_X, SpeedX));
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


    private void calculateTotalAcceleration() {
        SpeedY += GRAVITY;
        if (isOnGround()) {
            SpeedX *= (1 - FRICTION);
        }
        SpeedX *= (1 - AIR_RESISTANCE);
    }

    @Override
    public void update() {
        checkBounds();
        calculateTotalAcceleration();
        SpeedY = Math.min(MAX_SPEED_Y, SpeedY);
        X += SpeedX;
        Y += SpeedY;
    }
}

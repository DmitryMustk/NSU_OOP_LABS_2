package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.Vector2D;

public class BallModel implements UpdatableModel {


    private final Vector2D cords;
    private final Vector2D speed;

    private static final Vector2D MAX_SPEED = new Vector2D(3, 3);

    private static final double RADIUS = 12;
    private static final double GRAVITY = 0.01;
    private static final double AIR_RESISTANCE = 0.0005;
    private static final double SOLD_RESISTANCE = 0.02;
    private static final double BOUNCE_FACTOR = 0.7;

    private static final double MAX_SPEED_X = 3;
    private static final double MAX_SPEED_Y = 3;

    private static final int FLOOR = 400;

    public BallModel() {
        cords = new Vector2D(100, 100);
        speed = new Vector2D(0, 0);
    }

    public double getX() {
        return cords.getX();
    }

    public double getY() {
        return cords.getY();
    }

    public double getBallRadius() {
        return RADIUS;
    }

    public double getCentreX() {
        return cords.getX() - RADIUS;
    }

    public double getCentreY() {
        return cords.getY() - RADIUS;
    }

    public boolean isMove() {
        return Math.abs(cords.getX()) + Math.abs(cords.getY()) > 0.2;
    }

    public void kick(Direction direction) {
        speed.setX(speed.getX() + (direction == Direction.RIGHT ? 0.1: -0.1));
        speed.setY(speed.getY() - 0.2);
    }

    private void checkBounds() {
        if (cords.getY() + RADIUS >= FLOOR) {
            cords.setY( FLOOR - RADIUS);
            speed.setY(-speed.getY() * BOUNCE_FACTOR);
        }

        if (cords.getX() - RADIUS <= 0) {
            cords.setX(RADIUS);
            speed.setX(-speed.getX() * BOUNCE_FACTOR);
        }

        if (cords.getX() + RADIUS >= 1024) {
            cords.setX(1024 - RADIUS);
            speed.setX(-speed.getX() * BOUNCE_FACTOR);
        }

        if (cords.getY() - RADIUS <= 0) {
            cords.setY(0 + RADIUS);
            speed.setY(-speed.getY() * BOUNCE_FACTOR);
        }
    }
    private void calculateGravity() {
        if (cords.getY() + RADIUS < FLOOR) {
            speed.setY(speed.getY() + GRAVITY);
        }
    }

    private void calculateAirResistance() {
        if (speed.getX() != 0) {
            speed.setX( (speed.getX() / Math.abs(speed.getX())) * (Math.abs(speed.getX()) - AIR_RESISTANCE));
        }
    }

    private void calculateFrictionForce() {
        if (speed.getX() != 0 && cords.getY() + RADIUS > FLOOR - 3) {
            speed.setX( (speed.getX() / Math.abs(speed.getX())) * (Math.abs(speed.getX()) - SOLD_RESISTANCE));
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
        checkBounds();

        cords.addVector(speed);


        speed.setX( Math.min(speed.getX(), MAX_SPEED_X));
        speed.setY( Math.min(speed.getY(), MAX_SPEED_Y));
    }

}
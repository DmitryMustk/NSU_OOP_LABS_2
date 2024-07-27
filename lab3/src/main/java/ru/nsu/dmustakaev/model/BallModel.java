package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.utils.Bounds;
import ru.nsu.dmustakaev.utils.Vector2D;
import ru.nsu.dmustakaev.Main;

public class BallModel implements UpdatableModel {
    private final Vector2D cords;
    private final Vector2D speed;

    private static final double RADIUS = 12;
    private static final double GRAVITY = 0.01;
    private static final double AIR_RESISTANCE = 0.0005;
    private static final double SOLD_RESISTANCE = 0.01;
    private static final double BOUNCE_FACTOR = 0.7;

    private static final double MAX_SPEED_X = 3;
    private static final double MAX_SPEED_Y = 3;

    private static final int FLOOR = Main.SCREEN_HEIGHT * 2 / 3;

    public BallModel() {
        cords = new Vector2D(Main.SCREEN_WIDTH /2.0, FLOOR / 4.0);
        speed = new Vector2D(0, 0);
    }

    public double getX() {
        return cords.getX();
    }

    public double getY() {
        return cords.getY();
    }

    public double getRadius() {
        return RADIUS;
    }

    public boolean isMove() {
        return speed.getLength() > 0.3;
    }

    public void kick(Bounds bounds) {
        if(cords.getX() >= bounds.minX() - RADIUS / 2 && cords.getX() <= bounds.getCenterX() ) {
            cords.setX(bounds.minX() - RADIUS);
            speed.setX(-(speed.getX() + 3) * BOUNCE_FACTOR);
            speed.setY(-(speed.getY() + 2) * BOUNCE_FACTOR);
        }

        if (cords.getX() <= bounds.minX() + bounds.width() + RADIUS / 2 && cords.getX() >= bounds.getCenterX() - RADIUS ) {
            cords.setX(bounds.minX() + bounds.width() + RADIUS);
            speed.setX(-(speed.getX() - 3) * BOUNCE_FACTOR);
            speed.setY(-(speed.getY() + 2) * BOUNCE_FACTOR);
        }
    }

    private void checkBounds() {
        if (cords.getY() + RADIUS >= FLOOR || cords.getY() - RADIUS <= 0) {
            cords.setY(Math.min(Math.max(cords.getY(), RADIUS), FLOOR - RADIUS));
            speed.setY(-speed.getY() * BOUNCE_FACTOR);
        }

        if (cords.getX() - RADIUS <= 0 || cords.getX() + RADIUS >= Main.SCREEN_WIDTH) {
            cords.setX(Math.min(Math.max(cords.getX(), RADIUS), Main.SCREEN_WIDTH - RADIUS));
            speed.setX(-speed.getX() * BOUNCE_FACTOR);
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

    public void reset() {
        cords.copyVector(Main.SCREEN_WIDTH /2.0, FLOOR / 4.0);
        speed.copyVector(0, 0);
    }

    public Bounds getBounds() {
        return new Bounds(getX() - RADIUS, getY() - RADIUS, getRadius()* 2, getRadius() * 2);
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

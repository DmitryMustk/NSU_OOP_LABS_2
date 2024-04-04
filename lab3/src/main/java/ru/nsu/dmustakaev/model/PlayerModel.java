package ru.nsu.dmustakaev.model;

public class PlayerModel implements UpdatableModel {
    private Vector2D cords;
    private Vector2D speed;


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

    public PlayerModel() {
        cords = new Vector2D(100, 100);
        speed = new Vector2D();
    }

    public double getRadius() {
        return RADIUS;
    }

    public double getX() {
        return cords.getX();
    }

    public double getY() {
        return cords.getY();
    }

    private boolean isOnGround() {
        return cords.getY() + RADIUS >= FLOOR - FLOOR_DELTA;
    }

    public void jump() {
        if (isOnGround()) {
            speed.setY(JUMP_SPEED);
        }
    }

    public void move(Direction direction) {
        double accelerationX = direction == Direction.RIGHT ? 1 : -1;
        speed.addVector(accelerationX, 0);
        speed.setX(Math.min(MAX_SPEED_X, Math.max(-MAX_SPEED_X, speed.getX())));
    }

    private void checkBounds() {
        if (cords.getY() + RADIUS >= FLOOR) {
            cords.setY(FLOOR - RADIUS);
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
            cords.setY(RADIUS);
            speed.setY(-speed.getY() * BOUNCE_FACTOR);
        }
    }


    private void calculateTotalAcceleration() {
        speed.addVector(0, GRAVITY);
        if (isOnGround()) {
            speed.setX(speed.getX() * (1 - FRICTION));
        }
        speed.setX(speed.getX() * (1 - AIR_RESISTANCE));
    }

    @Override
    public void update() {
        checkBounds();
        calculateTotalAcceleration();
        speed.setY(Math.min(MAX_SPEED_Y, speed.getY()));
        cords.addVector(speed);
    }
}

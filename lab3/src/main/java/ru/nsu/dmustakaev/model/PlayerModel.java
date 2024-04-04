package ru.nsu.dmustakaev.model;

public class PlayerModel implements UpdatableModel {
    private Vector2D cords;
    private Vector2D speed;

    private boolean isMovingLeft;
    private boolean isMovingRight;


    private static final double RADIUS = 30 ;
    private static final double GRAVITY = 0.01;
    private static final double AIR_RESISTANCE = 0.01;
    private static final double FRICTION = 0.05;
    private static final double BOUNCE_FACTOR = 0.1;
    private static final double MOVEMENT_SPEED = 0.1;
    private static final double JUMP_SPEED = 15;
    private static final Vector2D MAX_SPEED = new Vector2D(2, 15);


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

    private Direction getDirectionOfMovement() {
        if(speed.getX() > 0){
            return Direction.RIGHT;
        }
        return Direction.LEFT;
    }

    public void jump() {
        if (!isOnGround()) {
            return;
        }

        speed.addVector(0, JUMP_SPEED);
    }

    public void stop(Direction direction) {
        if(direction == Direction.LEFT) {
            isMovingLeft = false;
        }
        else if (direction == Direction.RIGHT) {
            isMovingRight = false;
        }
    }

    public void move(Direction direction) {
        if(direction == Direction.LEFT) {
            isMovingLeft = true;
        }
        else if (direction == Direction.RIGHT) {
            isMovingRight = true;
        }
//        speed.addVector(accelerationX, 0);
//        speed.setX(Math.min(MAX_SPEED.getX(), Math.max(-MAX_SPEED.getX(), speed.getX())));
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


    private void calculateTotalSpeed() {
        double accelerationX = 0;
        if(isMovingLeft) {
            accelerationX = -MOVEMENT_SPEED;
        } else if (isMovingRight) {
            accelerationX = MOVEMENT_SPEED;
        }
        speed.addVector(accelerationX, 0);
        speed.setX(Math.min(MAX_SPEED.getX(), Math.max(-MAX_SPEED.getX(), speed.getX())));

        speed.addVector(0, GRAVITY);
        if (isOnGround()) {
            speed.setX(speed.getX() * (1 - FRICTION));
        }
        speed.setX(speed.getX() * (1 - AIR_RESISTANCE));
    }

    @Override
    public void update() {
        checkBounds();
        calculateTotalSpeed();
        speed.setY(Math.min(MAX_SPEED.getY(), speed.getY()));
        cords.addVector(speed);
    }
}

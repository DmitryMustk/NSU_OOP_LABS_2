package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.Vector2D;

public class PlayerModel extends PhysicalBody implements UpdatableModel {
    private static final Vector2D INIT_CORDS = new Vector2D(100, 100);

    private double movementSpeed = 0.1;
    private Vector2D maxSpeed = new Vector2D(1, 20);
    private double jumpSpeed = 20;

    private boolean isMovingLeft;
    private boolean isMovingRight;

    public PlayerModel() {
        super(
                new Vector2D(1.2, 5),
                30,
                0.1,
                new PhysicsSimulationSettings( 0.02, 0.01, 0.05)
        );

        setCords(INIT_CORDS);
    }

    public void jump() {
        if (!isOnGround()) {
            return;
        }

        getSpeed().addVector(0, jumpSpeed);
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
        isMovingRight = direction == Direction.RIGHT;
        isMovingLeft = direction == Direction.LEFT;
    }

    public void pushBack(Direction direction) {
        getSpeed().setX(getSpeed().getX() + (direction == Direction.RIGHT ? 0.5: -0.5));
        getSpeed().setY(getSpeed().getY() );
    }

    public void reset() {
        setSpeed(Vector2D.getZeroVector());
        setCords(INIT_CORDS);
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(double speed) {
        if (speed < 0) {
            throw new IllegalArgumentException("Speed is negative");
        }
        movementSpeed = speed;
    }

    public Vector2D getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    protected void calculateTotalSpeed() {
        double accelerationX = 0;
        if(isMovingLeft) {
            accelerationX = -movementSpeed;
        } else if (isMovingRight) {
            accelerationX = movementSpeed;
        }
        getSpeed().addVector(accelerationX, 0);
        getSpeed().setX(Math.min(maxSpeed.getX(), Math.max(-maxSpeed.getX(), getSpeed().getX())));

        getSpeed().addVector(0, getSimulationSettings().gravity());
        if (isOnGround()) {
            getSpeed().setX(getSpeed().getX() * (1 - getSimulationSettings().solidResistance()));
        }
        getSpeed().setX(getSpeed().getX() * (1 - getSimulationSettings().airResistance()));
    }


    @Override
    public void update() {
        calculateTotalSpeed();
        checkBounds();

        getSpeed().setY(Math.min(maxSpeed.getY(), getSpeed().getY()));
        getCords().addVector(getSpeed());
    }
}

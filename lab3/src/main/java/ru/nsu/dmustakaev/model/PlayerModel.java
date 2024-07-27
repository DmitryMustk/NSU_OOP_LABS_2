package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.Main;
import ru.nsu.dmustakaev.utils.Bounds;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.Vector2D;

public class PlayerModel extends PhysicalBody implements UpdatableModel {
    private static final double MOVEMENT_SPEED = 0.1;
    private static final double JUMP_SPEED = 20;
    private static final Vector2D MAX_SPEED = new Vector2D(1, 20);
    private static final Vector2D INIT_CORDS = new Vector2D(100, 100);

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

        getSpeed().addVector(0, JUMP_SPEED);
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
        getCords().copyVector(INIT_CORDS);
        getSpeed().copyVector(Vector2D.getZeroVector());
    }

    @Override
    protected void calculateTotalSpeed() {
        double accelerationX = 0;
        if(isMovingLeft) {
            accelerationX = -MOVEMENT_SPEED;
        } else if (isMovingRight) {
            accelerationX = MOVEMENT_SPEED;
        }
        getSpeed().addVector(accelerationX, 0);
        getSpeed().setX(Math.min(MAX_SPEED.getX(), Math.max(-MAX_SPEED.getX(), getSpeed().getX())));

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

        getSpeed().setY(Math.min(MAX_SPEED.getY(), getSpeed().getY()));
        getCords().addVector(getSpeed());
    }
}

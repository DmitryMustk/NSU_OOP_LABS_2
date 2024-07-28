package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.Vector2D;

public class EnemyModel extends PhysicalBody implements UpdatableModel {
    private static final Vector2D INIT_CORDS = new Vector2D(924, 100);
    private static final int FLOOR_DELTA = 3;

    private double movementSpeed = 0.1;
    private Vector2D maxSpeed = new Vector2D(1.2, 5);
    private double jumpSpeed = 15;


    private boolean isMovingLeft;
    private boolean isMovingRight;
    private boolean isEvil;

    private final BallModel ballModel;
    private final GoalModel leftGoalModel;
    private final GoalModel rightGoalModel;

    public EnemyModel(BallModel ballModel, GoalModel leftGoalModel, GoalModel rightGoalModel) {
        super(
                new Vector2D(1.2, 5),
                30,
                0.1, new PhysicsSimulationSettings( 0.02, 0.01, 0.05));
        setCords(INIT_CORDS);
        this.ballModel = ballModel;
        this.leftGoalModel = leftGoalModel;
        this.rightGoalModel = rightGoalModel;
        isEvil = false;
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

    public void setMaxSpeed(Vector2D maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getJumpSpeed() {
        return jumpSpeed;
    }

    public void setJumpSpeed(double jumpSpeed) {
        if (jumpSpeed < 0) {
            throw new IllegalArgumentException("JumpSpeed is negative");
        }
        this.jumpSpeed = jumpSpeed;
    }

    public void jump() {
        if (!isOnGround()) {
            return;
        }
        getSpeed().addVector(0, jumpSpeed);
    }

    public void move(Direction direction) {
        isMovingRight = direction == Direction.RIGHT;
        isMovingLeft = direction == Direction.LEFT;
    }

    private void calculateMovement() {
        double accelerationX = 0;
        accelerationX = isMovingLeft ? -movementSpeed : movementSpeed;
        getSpeed().addVector(accelerationX, 0);
        getSpeed().setX(Math.min(maxSpeed.getX(), Math.max(-maxSpeed.getX(), getSpeed().getX())));

    }

    public void pushBack(Direction direction) {
        getSpeed().setXY(0, 0);
        getSpeed().setX(getSpeed().getX() + (direction == Direction.RIGHT ? 0.2: -0.2));
        getSpeed().setY(getSpeed().getY() - 0.3);
    }

    public void processAI() {
        Direction dir = ballModel.getX() - getX() > 0 ? Direction.RIGHT : Direction.LEFT;
        double distanceToBall = Math.abs(ballModel.getX() - getX());
        double distanceToSelfGoal = rightGoalModel.getX() - getX();

        if(distanceToBall > 100 && distanceToSelfGoal > 400 && !isEvil) {
            dir = Direction.RIGHT;
        }
        move(dir);
        if(distanceToBall < 150 && Math.random() < 0.3) {
            jump();
        }
    }

    public void reset() {
        setSpeed(Vector2D.getZeroVector());
        setCords(INIT_CORDS);
        isEvil = !isEvil;
    }

    @Override
    public void update() {
        calculateTotalSpeed();
        calculateMovement();
        checkBounds();
        limitSpeed();
        getCords().addVector(getSpeed());
        processAI();
    }
}

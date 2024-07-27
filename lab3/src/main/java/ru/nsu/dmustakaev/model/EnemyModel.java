package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.Vector2D;

public class EnemyModel extends PhysicalBody implements UpdatableModel {

    private static final double MOVEMENT_SPEED = 0.1;
    private static final double JUMP_SPEED = 15;
    private static final Vector2D MAX_SPEED = new Vector2D(1.2, 5);
    private static final Vector2D INIT_CORDS = new Vector2D(924, 100);
    private static final int FLOOR_DELTA = 3;

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

    public void jump() {
        if (!isOnGround()) {
            return;
        }
        getSpeed().addVector(0, JUMP_SPEED);
    }

    public void move(Direction direction) {
        isMovingRight = direction == Direction.RIGHT;
        isMovingLeft = direction == Direction.LEFT;
    }

    private void calculateMovement() {
        double accelerationX = 0;
        accelerationX = isMovingLeft ? -MOVEMENT_SPEED : MOVEMENT_SPEED;
        getSpeed().addVector(accelerationX, 0);
        getSpeed().setX(Math.min(MAX_SPEED.getX(), Math.max(-MAX_SPEED.getX(), getSpeed().getX())));

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
        setCords(new Vector2D(924, 100));
        isEvil = !isEvil;
    }

    @Override
    public void update() {
        calculateTotalSpeed();
        calculateMovement();
        checkBounds();
        limitSpeed();
//        getSpeed().setY(Math.min(MAX_SPEED.getY(), getSpeed().getY()));
        getCords().addVector(getSpeed());
        processAI();
    }
}

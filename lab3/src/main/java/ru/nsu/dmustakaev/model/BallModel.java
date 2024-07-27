package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.utils.Bounds;
import ru.nsu.dmustakaev.utils.Vector2D;
import ru.nsu.dmustakaev.Main;

public class BallModel extends PhysicalBody implements UpdatableModel {
    private static final Vector2D INIT_CORDS = new Vector2D(Main.SCREEN_WIDTH / 2.0, PhysicsSimulationSettings.FLOOR / 4.0);

    public BallModel() {
        super(
                new Vector2D(3, 3),
                12,
                0.7,
                new PhysicsSimulationSettings(0.01, 0.0005, 0.01)
        );
        setCords(INIT_CORDS);
    }

    public boolean isMove() {
        return getSpeed().getLength() > 0.3;
    }

    public void kick(Bounds bounds) {
        if(getCords().getX() >= bounds.minX() - getRadius() / 2 && getCords().getX() <= bounds.getCenterX() ) {
            getCords().setX(bounds.minX() - getRadius());
            getSpeed().setX(-(getSpeed().getX() + 3) * getBounceFactor());
            getSpeed().setY(-(getSpeed().getY() + 2) * getBounceFactor());
        }

        if (getCords().getX() <= bounds.minX() + bounds.width() + getRadius() / 2 && getCords().getX() >= bounds.getCenterX() - getRadius() ) {
            getCords().setX(bounds.minX() + bounds.width() + getRadius());
            getSpeed().setX(-(getSpeed().getX() - 3) * getBounceFactor());
            getSpeed().setY(-(getSpeed().getY() + 2) * getBounceFactor());
        }
    }

    public void reset() {
        getCords().copyVector(Main.SCREEN_WIDTH /2.0, PhysicsSimulationSettings.FLOOR / 4.0);
        getSpeed().copyVector(0, 0);
    }

    @Override
    public void update() {
        calculateTotalSpeed();
        checkBounds();
        getCords().addVector(getSpeed());

        getSpeed().setX( Math.min(getSpeed().getX(), getMaxSpeed().getX()));
        getSpeed().setY( Math.min(getSpeed().getY(), getMaxSpeed().getY()));
    }
}

package ru.nsu.dmustakaev.model;

import ru.nsu.dmustakaev.Main;
import ru.nsu.dmustakaev.utils.Bounds;
import ru.nsu.dmustakaev.utils.Vector2D;

public class PhysicalBody {

    private Vector2D cords;
    private Vector2D speed;
    private final Vector2D maxSpeed;

    private final double radius;
    private final double bounceFactor;


    private final PhysicsSimulationSettings physicsSettings;

    public PhysicalBody(Vector2D maxSpeed, double radius, double bounceFactor, PhysicsSimulationSettings simulationSettings) {
        cords = new Vector2D();
        speed = new Vector2D();

        this.maxSpeed = maxSpeed;
        this.radius = radius;
        this.bounceFactor = bounceFactor;
        this.physicsSettings = simulationSettings;
    }

    public Vector2D getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2D speed) {
        this.speed = speed;
        limitSpeed();
    }

    public Vector2D getCords() {
        return cords;
    }

    public void setCords(Vector2D cords) {
        this.cords = cords;
        checkBounds();
    }

    public double getX() {
        return cords.getX();
    }

    public double getY() {
        return cords.getY();
    }

    public double getRadius() {
        return radius;
    }

    public double getBounceFactor() {
        return bounceFactor;
    }

    public Vector2D getMaxSpeed() {
        return maxSpeed;
    }

    public Bounds getBounds() {
        return new Bounds(
                cords.getX() - radius,
                cords.getY() - radius,
                radius * 2,
                radius * 2
                );
    }

    protected PhysicsSimulationSettings getSimulationSettings() {
        return physicsSettings;
    }

    protected void checkBounds() {
        if (cords.getY() + radius >= PhysicsSimulationSettings.FLOOR || cords.getY() - radius <= 0) {
            cords.setY(Math.min(Math.max(cords.getY(), radius), PhysicsSimulationSettings.FLOOR -radius));
            speed.setY(-speed.getY() * bounceFactor);
        }

        if (cords.getX() - radius <= 0 || cords.getX() + radius >= Main.SCREEN_WIDTH) {
            cords.setX(Math.min(Math.max(cords.getX(), radius), Main.SCREEN_WIDTH - radius));
            speed.setX(-speed.getX() * bounceFactor);
        }
    }

    protected boolean isOnGround() {
        return cords.getY() + radius >= PhysicsSimulationSettings.FLOOR - 3;
    }

    private void calculateGravity() {
        if (!isOnGround()) {
            speed.setY(speed.getY() + physicsSettings.gravity());
        }
    }

    private void calculateAirResistance() {
        if (speed.getX() != 0) {
            speed.setX( (speed.getX() / Math.abs(speed.getX())) * (Math.abs(speed.getX()) - physicsSettings.airResistance()));
        }
    }

    private void calculateFrictionForce() {
        if (speed.getX() != 0 && cords.getY() + radius > PhysicsSimulationSettings.FLOOR - 3) {
            speed.setX( (speed.getX() / Math.abs(speed.getX())) * (Math.abs(speed.getX()) - physicsSettings.solidResistance()));
        }
    }

    protected void limitSpeed() {
        speed.setX(Math.min(speed.getX(), maxSpeed.getX()));
        speed.setY(Math.min(speed.getY(), maxSpeed.getY()));
    }

    protected void calculateTotalSpeed() {
        calculateGravity();
        calculateFrictionForce();
        calculateAirResistance();
    }

}

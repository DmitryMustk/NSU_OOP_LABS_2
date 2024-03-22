package ru.nsu.dmustakaev.model;

import javafx.scene.input.KeyCode;

public class BallModel implements UpdatableModel {
    private double ballX = 100;
    private double ballY = 100;
    private double ballSpeedX = 0;
    private double ballSpeedY = 0;

    private static final double BALL_RADIUS = 15;
    private static final double GRAVITY = 0.01;
    private static final double AIR_RESISTANCE = 0.0005;
    private static final double BOUNCE_FACTOR = 0.8;

    private static final int FLOOR = 400;

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public double getBallRadius() {
        return BALL_RADIUS;
    }

    public double getCentreX() {
        return ballX - BALL_RADIUS;
    }

    public double getCentreY() {
        return ballY - BALL_RADIUS;
    }

    public boolean isMove() {
        return Math.abs(ballSpeedX) + Math.abs(ballSpeedY) > 0.2;
    }

    public void kick(KeyCode keyCode) {
        ballSpeedX += keyCode != KeyCode.LEFT ? 1 : -1;
        ballSpeedY -= 1.5;
    }

    private void checkBounds() {
        if (ballY + BALL_RADIUS >= FLOOR) {
            ballY = FLOOR - BALL_RADIUS;
            ballSpeedY = -ballSpeedY * BOUNCE_FACTOR;
        }

        if (ballX - BALL_RADIUS <= 0) {
            ballX = BALL_RADIUS;
            ballSpeedX = -ballSpeedX * BOUNCE_FACTOR;
        }

        if (ballX + BALL_RADIUS >= 1024) {
            ballX = 1024 - BALL_RADIUS;
            ballSpeedX = -ballSpeedX * BOUNCE_FACTOR;
        }

        if (ballY - BALL_RADIUS <= 0) {
            ballY = 0 + BALL_RADIUS;
            ballSpeedY = -ballSpeedY * BOUNCE_FACTOR;
        }
    }
    private void calculateGravity() {
        if (ballY + BALL_RADIUS < FLOOR) {
            ballSpeedY += GRAVITY;
        }
    }

    private void calculateAirResistance() {
        if (ballSpeedX != 0) {
            ballSpeedX = (ballSpeedX / Math.abs(ballSpeedX)) * (Math.abs(ballSpeedX) - AIR_RESISTANCE);
        }
    }

    private void calculateFrictionForce() {
        if (ballSpeedX != 0 && ballY + BALL_RADIUS > FLOOR - 3) {
            ballSpeedX = (ballSpeedX / Math.abs(ballSpeedX)) * (Math.abs(ballSpeedX) - 0.02);
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
        System.out.println("x: " + ballX + " y: " + ballY);
        checkBounds();
        ballX += ballSpeedX;
        ballY += ballSpeedY;

    }
}

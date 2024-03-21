package ru.nsu.dmustakaev.model;

public class BallModel {
    private double ballX = 100;
    private double ballY = 100;
    private double ballRadius = 20;
    private double ballSpeedX = 0;
    private double ballSpeedY = 0;
    private double gravity = 0.01;
    private double air_resistance = 0.01;

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public double getBallRadius() {
        return ballRadius;
    }

    public void kick() {
        ballSpeedX += 1;
        ballSpeedY -= 1;
    }

    private void checkBounds() {
        if (ballY + ballRadius >= 400) {
            ballY = 400 - ballRadius;
            ballSpeedY = -ballSpeedY * 0.8;
        }

        if (ballX - ballRadius <= 0) {
            ballX = ballRadius;
            ballSpeedX = -ballSpeedX * 0.8;
        }

        if (ballX + ballRadius >= 1024) {
            ballX = 1024 - ballRadius;
            ballSpeedX = -ballSpeedX * 0.8;
        }

        if (ballY - ballRadius <= 0) {
            ballY = 0 + ballRadius;
            ballSpeedY = -ballSpeedY * 0.8;
        }
    }

    public void update() {
        ballSpeedY += gravity;
        ballSpeedX -= ballSpeedX / Math.abs(ballSpeedX)
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        checkBounds();
    }
}

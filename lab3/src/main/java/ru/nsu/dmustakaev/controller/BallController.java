package ru.nsu.dmustakaev.controller;

import javafx.animation.AnimationTimer;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.view.BallView;

public class BallController {
    private BallView view;
    private BallModel model;

    public BallController(BallView view, BallModel model) {
        this.view = view;
        this.model = model;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                view.update();
            }
        };

        timer.start();
    }

    public void hitBall() {
        model.kick();
    }
}

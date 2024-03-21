package ru.nsu.dmustakaev.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ru.nsu.dmustakaev.model.BallModel;

public class BallView {
    private BallModel model;
    private Pane pane;
    private Circle ball;

    public BallView(BallModel model) {
        this.model = model;

        pane = new Pane();
        ball = new Circle(model.getBallX(), model.getBallY(), model.getBallRadius(), Color.RED);
        pane.getChildren().add(ball);
    }

    public Pane getPane() {
        return pane;
    }

    public void update() {
        model.update();
        ball.setCenterX(model.getBallX());
        ball.setCenterY(model.getBallY());
    }
}

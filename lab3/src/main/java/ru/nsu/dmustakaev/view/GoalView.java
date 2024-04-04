package ru.nsu.dmustakaev.view;

import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.nsu.dmustakaev.model.GoalModel;

public class GoalView {
    private final GoalModel model;
    private final Pane pane;
    private final Rectangle goalRectangle;

    public GoalView(GoalModel model) {
        this.model = model;

        pane = new Pane();
        goalRectangle = new Rectangle(model.getX(), model.getY(), model.getHeight(), model.getWidth());
        goalRectangle.setFill(Color.TRANSPARENT);
        goalRectangle.setStroke(Color.BLACK);
        pane.getChildren().add(goalRectangle);
    }

    public Pane getPane() {
        return pane;
    }

    public Bounds getBounds() {
        return goalRectangle.localToScene(goalRectangle.getBoundsInLocal());
    }
}
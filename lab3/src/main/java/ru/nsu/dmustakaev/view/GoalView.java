package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.nsu.dmustakaev.model.GoalModel;
import ru.nsu.dmustakaev.utils.Bounds;
import ru.nsu.dmustakaev.utils.Direction;

public class GoalView implements GameObjectView {
    private final GoalModel model;
    private final Pane pane;
    private final ImageView goalView;
    private final Direction direction;

    private static final String LEFT_TEXTURE_PATH = "/game/pictures/model_textures/goal/left_goal_texture.png";
    private static final String RIGHT_TEXTURE_PATH = "/game/pictures/model_textures/goal/right_goal_texture.png";

    public GoalView(GoalModel model, Direction direction) {
        this.model = model;
        this.direction = direction;

        Image texture = direction == Direction.LEFT ? new Image(LEFT_TEXTURE_PATH) : new Image(RIGHT_TEXTURE_PATH);
        goalView = new ImageView(texture);
        goalView.setFitWidth(model.getWidth());
        goalView.setFitHeight(model.getHeight());
        goalView.setTranslateX(model.getX());
        goalView.setTranslateY(model.getY());

        pane = new Pane();
        pane.getChildren().add(goalView);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public Bounds getBounds() {
//        return goalView.localToScene(goalView.getBoundsInLocal());
        return  model.getBounds();
    }
}
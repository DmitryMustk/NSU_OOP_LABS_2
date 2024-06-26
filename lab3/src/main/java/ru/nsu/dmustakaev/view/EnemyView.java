package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.nsu.dmustakaev.model.EnemyModel;
import ru.nsu.dmustakaev.utils.Bounds;

public class EnemyView implements GameObjectView {
    private final EnemyModel model;
    private final Pane pane;
    private final ImageView enemyView;

    private static final String TEXTURE_PATH = "/enemy_texture.png";

    public EnemyView(EnemyModel model) {
        this.model = model;

        enemyView = new ImageView(new Image(TEXTURE_PATH));
        enemyView.setFitWidth(model.getRadius() * 2);
        enemyView.setFitHeight(model.getRadius() * 2);
        enemyView.setTranslateX(model.getX() - model.getRadius());
        enemyView.setTranslateY(model.getY() - model.getRadius());

        pane = new Pane();
        pane.getChildren().add(enemyView);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public void update() {
        model.update();
        enemyView.setTranslateX(model.getX() - model.getRadius());
        enemyView.setTranslateY(model.getY() - model.getRadius());
    }

    public Bounds getBounds() {
//        return playerView.localToScene(playerView.getBoundsInLocal());
//        return new Bounds(1, 2, 3, 4, 5, 7);
        return model.getBounds();
    }
}
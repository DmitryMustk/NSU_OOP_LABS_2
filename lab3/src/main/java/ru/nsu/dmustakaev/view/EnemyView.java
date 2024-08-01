package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.nsu.dmustakaev.model.EnemyModel;

public class EnemyView implements GameObjectView {
    private final EnemyModel model;
    private final Pane pane;
    private final ImageView enemyView;

    private static final String TEXTURE_PATH = "/game/pictures/model_textures/enemy_texture.png";

    public EnemyView(EnemyModel model) {
        this.model = model;

        enemyView = new ImageView(new Image(TEXTURE_PATH));

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

        enemyView.setFitWidth(model.getRadius() * 2);
        enemyView.setFitHeight(model.getRadius() * 2);
    }
}
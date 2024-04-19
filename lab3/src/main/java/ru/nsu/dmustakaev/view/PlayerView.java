package ru.nsu.dmustakaev.view;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.nsu.dmustakaev.model.PlayerModel;

public class PlayerView implements GameObjectView {
    private final PlayerModel model;
    private final Pane pane;
    private final ImageView player;

    private static final String TEXTURE_PATH = "/player_texture.png";

    public PlayerView(PlayerModel model) {
        this.model = model;

        pane = new Pane();
        Image texture = new Image(TEXTURE_PATH);

        player = new ImageView(texture);
        player.setFitWidth(model.getRadius() * 2);
        player.setFitHeight(model.getRadius() * 2);
        player.setTranslateX(model.getX() - model.getRadius());
        player.setTranslateY(model.getY() - model.getRadius());
        pane.getChildren().add(player);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public void update() {
        model.update();
        player.setTranslateX(model.getX() - model.getRadius());
        player.setTranslateY(model.getY() - model.getRadius());
    }

    public Bounds getBounds() {
        return player.localToScene(player.getBoundsInLocal());
    }
}

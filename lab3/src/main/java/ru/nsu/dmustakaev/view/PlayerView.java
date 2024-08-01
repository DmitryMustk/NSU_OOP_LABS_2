package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.nsu.dmustakaev.model.PlayerModel;
import ru.nsu.dmustakaev.utils.Bounds;

public class PlayerView implements GameObjectView {
    private final PlayerModel model;
    private final Pane pane;
    private final ImageView playerView;

    private static final String TEXTURE_PATH = "/game/pictures/model_textures/player_texture.png";

    public PlayerView(PlayerModel model) {
        this.model = model;

        playerView = new ImageView(new Image(TEXTURE_PATH));

        pane = new Pane();
        pane.getChildren().add(playerView);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    @Override
    public void update() {
        model.update();
        playerView.setTranslateX(model.getX() - model.getRadius());
        playerView.setTranslateY(model.getY() - model.getRadius());

        playerView.setFitWidth(model.getRadius() * 2);
        playerView.setFitHeight(model.getRadius() * 2);
    }
}

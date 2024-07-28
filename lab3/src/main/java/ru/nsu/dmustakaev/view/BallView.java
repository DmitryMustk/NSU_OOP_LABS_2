package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.utils.Bounds;

public class BallView implements GameObjectView, DynamicGameObjectView {
    private final BallModel model;
    private final Pane pane;
    private final ImageView ballImageView;

    private final Image texture;
    private final Image mirrorTexture;

    private static final String TEXTURE_PATH = "/game/pictures/model_textures/ball/soccer_ball_texture.png";
    private static final String MIRROR_TEXTURE_PATH = "/game/pictures/model_textures/ball/soccer_ball_texture_mirrored.png";

    public BallView(BallModel model) {
        this.model = model;

        texture = new Image(TEXTURE_PATH);
        mirrorTexture = new Image(MIRROR_TEXTURE_PATH);

        ballImageView = new ImageView(texture);

        pane = new Pane();
        pane.getChildren().add(ballImageView);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public void animate() {
        if (ballImageView.getImage() == texture) {
            ballImageView.setImage(mirrorTexture);
            return;
        }
        ballImageView.setImage(texture);
    }

    @Override
    public void update() {
        model.update();

        ballImageView.setTranslateX(model.getX() - model.getRadius());
        ballImageView.setTranslateY(model.getY() - model.getRadius());

        ballImageView.setFitWidth(model.getRadius() * 2);
        ballImageView.setFitHeight(model.getRadius() * 2);

        if (model.isMove()) {
            animate();
        }
    }

    public Bounds getBounds() {
        return model.getBounds();
    }
}

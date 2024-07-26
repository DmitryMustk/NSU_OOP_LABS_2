package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.utils.Bounds;

public class BallView implements GameObjectView {
    private final BallModel model;
    private final Pane pane;
    private final ImageView ballView;

    private final Image texture;
    private final Image mirrorTexture;

    private static final String TEXTURE_PATH = "/game/pictures/model_textures/ball/soccer_ball_texture.png";
    private static final String MIRROR_TEXTURE_PATH = "/game/pictures/model_textures/ball/soccer_ball_texture_mirrored.png";

    public BallView(BallModel model) {
        this.model = model;

        texture = new Image(TEXTURE_PATH);
        mirrorTexture = new Image(MIRROR_TEXTURE_PATH);

        ballView = new ImageView(texture);

        ballView.setFitWidth(model.getRadius() * 2);
        ballView.setFitHeight(model.getRadius() * 2);
        ballView.setTranslateX(model.getX() - model.getRadius());
        ballView.setTranslateY(model.getY() - model.getRadius());

        pane = new Pane();
        pane.getChildren().add(ballView);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

    public void animate() {
        if (ballView.getImage() == texture) {
            ballView.setImage(mirrorTexture);
            return;
        }
        ballView.setImage(texture);
    }

    public void update() {
        model.update();
        ballView.setTranslateX(model.getX() - model.getRadius());
        ballView.setTranslateY(model.getY() - model.getRadius());
        if (model.isMove()) {
            animate();
        }
    }

    public Bounds getBounds() {
//        return ballView.localToScene(ballView.getBoundsInLocal());
        return model.getBounds();
    }
}

package ru.nsu.dmustakaev.view;

import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ru.nsu.dmustakaev.model.BallModel;

import java.util.Objects;

public class BallView {
    private final BallModel model;
    private final Pane pane;
    private final ImageView ball;

    private final Image texture;
    private final Image mirrorTexture;

    private static final String TEXTURE_PATH = "/soccer_ball_texture.png";
    private static final String MIRROR_TEXTURE_PATH = "/soccer_ball_texture_mirrored.png";

    public BallView(BallModel model) {
        this.model = model;

        pane = new Pane();
        texture = new Image(TEXTURE_PATH);
        mirrorTexture = new Image(MIRROR_TEXTURE_PATH);

        ball = new ImageView(texture);

        ball.setFitWidth(model.getBallRadius() * 2);
        ball.setFitHeight(model.getBallRadius() * 2);
        ball.setTranslateX(model.getCentreX());
        ball.setTranslateY(model.getCentreY());
        pane.getChildren().add(ball);
    }

    public Pane getPane() {
        return pane;
    }

    public void animate() {
        if(ball.getImage() == texture) {
            ball.setImage(mirrorTexture);
            return;
        }
        ball.setImage(texture);
    }

    public void update() {
        model.update();
        ball.setTranslateX(model.getCentreX());
        ball.setTranslateY(model.getCentreY());
        if (model.isMove()) {
            animate();
        }
    }

    public Bounds getBounds() {
        return ball.localToScene(ball.getBoundsInLocal());
    }

}

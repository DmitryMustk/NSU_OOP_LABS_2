package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BackgroundView {
    private final Pane pane;
    private final ImageView background;

    private static final String TEXTURE_PATH = "/background.png";

    public BackgroundView() {


        pane = new Pane();
        Image texture = new Image(TEXTURE_PATH);

        background = new ImageView(texture);
        background.setFitWidth(1024);
        background.setFitHeight(768);
//        player.setTranslateX(model.getX());
//        player.setTranslateY(model.getY());
        pane.getChildren().add(background);
    }

    public Pane getPane() {
        return pane;
    }

}
package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class FieldView {
    private final Pane pane;
    private final ImageView field;

    private static final String TEXTURE_PATH = "/field_texture.png";

    public FieldView() {
        pane = new Pane();
        Image texture = new Image(TEXTURE_PATH);

        field = new ImageView(texture);
        field.setFitWidth(1024);
        field.setFitHeight(368);
//        player.setTranslateX(model.getX());
        field.setTranslateY(400);
        pane.getChildren().add(field);
    }

    public Pane getPane() {
        return pane;
    }

}
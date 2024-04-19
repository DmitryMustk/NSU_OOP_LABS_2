package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import ru.nsu.dmustakaev.Main;

public class FieldView implements GameObjectView {
    private final Pane pane;
    private final ImageView field;

    private static final String TEXTURE_PATH = "/field_texture.png";

    public FieldView() {
        pane = new Pane();
        Image texture = new Image(TEXTURE_PATH);

        field = new ImageView(texture);
        field.setFitWidth(Main.SCREEN_WIDTH);
        field.setFitHeight(Main.SCREEN_HEIGHT);

        field.setTranslateY(Main.SCREEN_HEIGHT * 2 / 3.0);
        pane.getChildren().add(field);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

}
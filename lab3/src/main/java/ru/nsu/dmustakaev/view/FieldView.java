package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import ru.nsu.dmustakaev.Main;

public class FieldView implements GameObjectView {
    private final Pane pane;
    private final ImageView field;

    private static final String TEXTURE_PATH = "/game/pictures/field_texture.png";

    public FieldView() {
        field = new ImageView(new Image(TEXTURE_PATH));
        field.setFitWidth(Main.SCREEN_WIDTH);
        field.setFitHeight(Main.SCREEN_HEIGHT);
        field.setTranslateX(0);
        field.setTranslateY(Main.SCREEN_HEIGHT * 2 / 3.0);

        pane = new Pane();
        pane.getChildren().add(field);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

}
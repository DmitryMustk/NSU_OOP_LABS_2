package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import ru.nsu.dmustakaev.Main;

public class BackgroundView implements GameObjectView {
    private final Pane pane;
    private final ImageView background;

    private static final String TEXTURE_PATH = "/background_game.png";

    public BackgroundView() {


        pane = new Pane();
        Image texture = new Image(TEXTURE_PATH);

        background = new ImageView(texture);
        background.setFitWidth(Main.SCREEN_WIDTH);
        background.setFitHeight(Main.SCREEN_HEIGHT);

        pane.getChildren().add(background);
    }

    @Override
    public Pane getPane() {
        return pane;
    }

}
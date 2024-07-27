package ru.nsu.dmustakaev.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import ru.nsu.dmustakaev.Main;

public class BackgroundView implements GameObjectView {
    private final Pane pane;
    private static final String TEXTURE_PATH = "/game/pictures/background_game.png";

    public BackgroundView() {
        Image texture = new Image(TEXTURE_PATH);
        ImageView background = new ImageView(texture);
        background.setFitWidth(Main.SCREEN_WIDTH);
        background.setFitHeight(Main.SCREEN_HEIGHT);

        pane = new Pane();
        pane.getChildren().add(background);
    }

    @Override
    public Pane getPane() {
        return pane;
    }
}
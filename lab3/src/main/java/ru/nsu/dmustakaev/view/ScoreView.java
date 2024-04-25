package ru.nsu.dmustakaev.view;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import ru.nsu.dmustakaev.Main;

public class ScoreView implements  GameObjectView{
    private final Pane pane;
    private final Label scoreLabel;

    private static final String INIT_LABEL_TEXT = "0:0";
    private static final String LABEL_FONT = "Arial";
    private static final int LABEL_FONT_SIZE = 48;

    public ScoreView() {
        scoreLabel = new Label(INIT_LABEL_TEXT);

        scoreLabel.setFont(Font.font(LABEL_FONT, FontWeight.BOLD, LABEL_FONT_SIZE));
        scoreLabel.setTextAlignment(TextAlignment.CENTER);
        scoreLabel.setTextFill(Color.WHITE);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(1);
        dropShadow.setOffsetY(1);
        dropShadow.setColor(Color.BLACK);
        scoreLabel.setEffect(dropShadow);

        double textWidth = scoreLabel.prefWidth(-1);

        scoreLabel.setLayoutX((Main.SCREEN_WIDTH - textWidth) / 2);;
        scoreLabel.setLayoutY(40);

        pane = new Pane();
        pane.getChildren().add(scoreLabel);
    }

    public void reset(int playerScore, int enemyScore) {
        scoreLabel.setText(playerScore + ":" + enemyScore);
    }

    @Override
    public Pane getPane() {
        return pane;
    }
}

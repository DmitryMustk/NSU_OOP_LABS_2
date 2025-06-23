package ru.nsu.dmustakaev.animations;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class GameModeAnimation {
    public static void run(Label gameModeLabel, String modeName) {
        gameModeLabel.setText(modeName);
        gameModeLabel.setVisible(true);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), gameModeLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(0.5), gameModeLabel);
        scaleUp.setFromX(0.5);
        scaleUp.setFromY(0.5);
        scaleUp.setToX(1.5);
        scaleUp.setToY(1.5);

        ScaleTransition scaleDown = new ScaleTransition(Duration.seconds(0.5), gameModeLabel);
        scaleDown.setFromX(1.5);
        scaleDown.setFromY(1.5);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), gameModeLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(1.5));
        fadeOut.setOnFinished(e -> gameModeLabel.setVisible(false));

        SequentialTransition seqTransition = new SequentialTransition(fadeIn, scaleUp, scaleDown, fadeOut);
        seqTransition.play();

        TranslateTransition flameTransition = new TranslateTransition(Duration.seconds(0.5), gameModeLabel);
        flameTransition.setFromY(-10);
        flameTransition.setToY(10);
        flameTransition.setCycleCount(TranslateTransition.INDEFINITE);
        flameTransition.setAutoReverse(true);

        flameTransition.play();
    }
}

package ru.nsu.dmustakaev.controller.handlers;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.SoundEngine;

public class GameOverHandler {
    private final SoundEngine soundEngine;
    private final AnchorPane endGameRoot;
    private final ImageView winScreenPicture;
    private final ImageView loseScreenPicture;
    private final ImageView exitToMainMenuButton;

    public GameOverHandler(SoundEngine soundEngine,
                           AnchorPane endGameRoot,
                           ImageView winScreenPicture,
                           ImageView loseScreenPicture,
                           ImageView exitToMainMenuButton
    ) {
        this.soundEngine = soundEngine;
        this.endGameRoot = endGameRoot;
        this.winScreenPicture = winScreenPicture;
        this.loseScreenPicture = loseScreenPicture;
        this.exitToMainMenuButton = exitToMainMenuButton;
    }

    public void handleGameOver(Direction winner) {
        soundEngine.stopMusic();

        endGameRoot.toFront();
        exitToMainMenuButton.setVisible(true);

        if (winner == Direction.LEFT) {
            soundEngine.setMusic("/game/sounds/game_over_music/win_music.mp3");
            soundEngine.playMusic();
            winScreenPicture.setVisible(true);
        } else if (winner == Direction.RIGHT) {
            soundEngine.setMusic("/game/sounds/game_over_music/lose_music.mp3");
            soundEngine.playMusic();
            loseScreenPicture.setVisible(true);
        }
    }
}

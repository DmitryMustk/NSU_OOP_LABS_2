package ru.nsu.dmustakaev.engine;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;
import ru.nsu.dmustakaev.model.*;
import ru.nsu.dmustakaev.modes.*;
import ru.nsu.dmustakaev.utils.Bounds;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.SoundEngine;
import ru.nsu.dmustakaev.view.*;

import java.util.*;

public class GameEngine {
    private static final int FPS = 240;
    private boolean isOnPause = false;

    private GameState gameState;

    private List<GameMode> gameModes;
    private final ObjectProperty<GameMode> currentGameMode;

    private List<GameObjectView> objectViews;

    private final SoundEngine soundEngine;

    private final Random random;

    private final BooleanProperty isFinished;
    private static final int GOALS_TO_WIN = 5;
    private Direction winner;

    public GameEngine(SoundEngine soundEngine) {
        this.soundEngine = soundEngine;
        this.random = new Random();
        this.isFinished = new SimpleBooleanProperty(false);
        this.currentGameMode = new SimpleObjectProperty<>(null);


        this.gameState = GameObjectFactory.createGameState();
        this.objectViews = GameObjectFactory.createViews(this.gameState);
        this.gameModes = GameObjectFactory.createGameModes(this.gameState);

        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / FPS), actionEvent -> {
            if (isOnPause || isFinished.get()) {
                return;
            }
            objectViews.forEach(GameObjectView::update);
            checkCollision();
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setPause(boolean pause) {
        isOnPause = pause;
    }

    public List<GameObjectView> getGameObjectViews() {
        return objectViews;
    }

    public PlayerModel getPlayerModel() {
        return gameState.playerModel();
    }

    private void checkCollision() {
        Bounds ballBounds = gameState.ballModel().getBounds();
        Bounds playerBounds = gameState.playerModel().getBounds();
        Bounds enemyBounds = gameState.enemyModel().getBounds();
        Bounds leftGoalBounds = gameState.leftGoalModel().getBounds();
        Bounds rightGoalBounds = gameState.rightGoalModel().getBounds();

        if (ballBounds.intersects(playerBounds))
            gameState.ballModel().kick(playerBounds);
        if (ballBounds.intersects(enemyBounds))
            gameState.ballModel().kick(enemyBounds);
        if (rightGoalBounds.intersects(ballBounds))
            handleScore(Direction.LEFT);
        if (leftGoalBounds.intersects(ballBounds))
            handleScore(Direction.RIGHT);

        if (playerBounds.intersects(enemyBounds)) {
            Direction playerPushDirection = gameState.playerModel().getX() < gameState.enemyModel().getX() ? Direction.LEFT : Direction.RIGHT;
            Direction enemyPushDirection = playerPushDirection == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
            gameState.playerModel().pushBack(playerPushDirection);
            gameState.enemyModel().pushBack(enemyPushDirection);
        }
    }

    private void handleScore(Direction whoScored)  {
        if (whoScored != Direction.LEFT && whoScored != Direction.RIGHT) {
            throw new IllegalArgumentException("Wrong direction");
        }

        if (whoScored == Direction.LEFT) {
            soundEngine.playSound("/game/sounds/score/sii.mp3");
            gameState.scoreModel().incrementPlayerScore();
        } else {
            soundEngine.playSound("/game/sounds/score/fail.mp3");
            gameState.scoreModel().incrementEnemyScore();
        }
        handleGameOver();
        pauseAfterScore();
    }

    private void handleGameOver() {
        int playerScore = gameState.scoreModel().getPlayerScore();
        int enemyScore = gameState.scoreModel().getEnemyScore();

        if(playerScore == GOALS_TO_WIN || enemyScore == GOALS_TO_WIN) {
            winner = playerScore > enemyScore ? Direction.LEFT : Direction.RIGHT;
            isFinished.set(true);
        }
    }

    private void pauseAfterScore() {
        if(isFinished.get()) {
            return;
        }

        isOnPause = true;
        PauseTransition pause = new PauseTransition(Duration.seconds(1.3));
        pause.setOnFinished(event -> {
            resetModels();
            applyNewMode();
            isOnPause = false;
        });
        pause.play();
    }

    private void resetModels() {
        gameState.reset();
    }

    private void applyNewMode() {
        if (currentGameMode.get() != null) {
            currentGameMode.get().unapply();
        }
        currentGameMode.set(
                gameModes.stream()
                .filter(g -> !g.equals(currentGameMode))
                .toList()
                .get((random.nextInt(gameModes.size() - 1)))
        );

        currentGameMode.get().apply();
        soundEngine.playSound(currentGameMode.get().getSoundSource());
    }

    public ObjectProperty<GameMode> currentGameModeProperty() {
        return currentGameMode;
    }

    public BooleanProperty getIsFinished() {
        return isFinished;
    }

    public Direction getWinner() {
        return winner;
    }
}

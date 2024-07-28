package ru.nsu.dmustakaev;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ru.nsu.dmustakaev.model.*;
import ru.nsu.dmustakaev.modes.*;
import ru.nsu.dmustakaev.utils.Bounds;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.utils.SoundEngine;
import ru.nsu.dmustakaev.view.*;

import java.util.*;
import java.util.stream.Stream;

import static ru.nsu.dmustakaev.Main.SCREEN_HEIGHT;
import static ru.nsu.dmustakaev.Main.SCREEN_WIDTH;

public class GameEngine {
    private static final int FPS = 240;
    private boolean isOnPause = false;

    private BallModel ballModel;
    private PlayerModel playerModel;
    private EnemyModel enemyModel;
    private GoalModel leftGoalModel;
    private GoalModel rightGoalModel;
    private ScoreModel scoreModel;

    private List<GameMode> gameModes;
    private GameMode currentGameMode;

    private List<GameObjectView> staticGameObjectViews;
    private List<DynamicGameObjectView> dynamicGameObjectViews;

    private final SoundEngine soundEngine;

    private final Random random;

    private void createGameObjectsModels() {
        leftGoalModel = new GoalModel(Direction.LEFT,0,  240, 160, 20);
        rightGoalModel = new GoalModel(Direction.RIGHT,SCREEN_WIDTH - 20, SCREEN_HEIGHT - 360, 160, 20);

        ballModel = new BallModel();

        playerModel = new PlayerModel();
        enemyModel = new EnemyModel(ballModel, leftGoalModel, rightGoalModel);
        scoreModel = new ScoreModel();
    }

    private void createGameObjectViews() {
        staticGameObjectViews = new ArrayList<>();
        staticGameObjectViews.addAll(Arrays.asList(
                new BackgroundView(),
                new FieldView()
        ));

        dynamicGameObjectViews = new ArrayList<>();
        dynamicGameObjectViews.addAll(Arrays.asList(
                new BallView(ballModel),
                new PlayerView(playerModel),
                new EnemyView(enemyModel),
                new ScoreView(scoreModel),
                new GoalView(leftGoalModel),
                new GoalView(rightGoalModel)
        ));
    }

    private void createGameModes() {
        gameModes = new ArrayList<>();
        gameModes.addAll(Arrays.asList(
                new DefaultGameMode(),

                new BigGoalsGameMode(leftGoalModel, rightGoalModel),
                new SmallGoalsGameMode(leftGoalModel, rightGoalModel),

                new BigBallGameMode(ballModel),
                new SmallBallGameMode(ballModel),

                new BigPlayersGameMode(playerModel, enemyModel),
                new SmallPlayersGameMode(playerModel, enemyModel),

                new LightBallGameMode(ballModel),
                new HeavyBallGameMode(ballModel),

                new MoonGravityGameMode(ballModel, playerModel, enemyModel),

                new PlayersHighSpeedGameMode(playerModel, enemyModel),

                new SlipperyFloorGameMode(ballModel, playerModel, enemyModel)
        ));
    }

    public GameEngine(SoundEngine soundEngine) {
        createGameObjectsModels();
        createGameObjectViews();
        createGameModes();

        this.soundEngine = soundEngine;
        this.random = new Random();

        KeyFrame frame = new KeyFrame(Duration.seconds(1.0 / FPS), actionEvent -> {
            if (isOnPause) {
                return;
            }
            dynamicGameObjectViews.forEach(DynamicGameObjectView::update);
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
        return Stream.concat(
                staticGameObjectViews.stream(),
                dynamicGameObjectViews.stream()
        ).toList();
    }

    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    private void checkCollision() {
        Bounds ballBounds = ballModel.getBounds();
        Bounds playerBounds = playerModel.getBounds();
        Bounds enemyBounds = enemyModel.getBounds();
        Bounds leftGoalBounds = leftGoalModel.getBounds();
        Bounds rightGoalBounds = rightGoalModel.getBounds();

        if (ballBounds.intersects(playerBounds)) {
            ballModel.kick(playerBounds);
        }

        if (ballBounds.intersects(enemyBounds)) {
            ballModel.kick(enemyBounds);
        }

        if (rightGoalBounds.intersects(ballBounds)) {
            handleScore(Direction.LEFT);
        }

        if (leftGoalBounds.intersects(ballBounds)) {
            handleScore(Direction.RIGHT);
        }

        if (playerBounds.intersects(enemyBounds)) {
            Direction playerPushDirection = playerModel.getX() < enemyModel.getX() ? Direction.LEFT : Direction.RIGHT;
            Direction enemyPushDirection = playerPushDirection == Direction.LEFT ? Direction.RIGHT : Direction.LEFT;
            playerModel.pushBack(playerPushDirection);
            enemyModel.pushBack(enemyPushDirection);
        }
    }

    private void handleScore(Direction whoScored)  {
        if (whoScored != Direction.LEFT && whoScored != Direction.RIGHT) {
            throw new IllegalArgumentException("Wrong direction");
        }

        if (whoScored == Direction.LEFT) {
            soundEngine.playSound("/game/sounds/score/sii.mp3");
            scoreModel.incrementPlayerScore();
        } else {
            soundEngine.playSound("/game/sounds/score/fail.mp3");
            scoreModel.incrementEnemyScore();
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
        ballModel.reset();
        playerModel.reset();
        enemyModel.reset();
    }

    private void applyNewMode() {
        if (currentGameMode != null) {
            currentGameMode.unapply();
        }
//        currentGameMode = gameModes.getFirst();
        currentGameMode = gameModes.stream()
                .filter(g -> !g.equals(currentGameMode))
                .toList()
                .get((random.nextInt(gameModes.size() - 1)))
        ;

        currentGameMode.apply();
        System.out.println("Current game mode: " + currentGameMode);
        soundEngine.playSound(currentGameMode.getSoundSource());
    }

    public GameMode getCurrentGameMode() {
        return currentGameMode;
    }

}

package ru.nsu.dmustakaev.engine;

import ru.nsu.dmustakaev.model.*;
import ru.nsu.dmustakaev.modes.*;
import ru.nsu.dmustakaev.utils.Direction;
import ru.nsu.dmustakaev.view.*;

import java.util.List;

import static ru.nsu.dmustakaev.Main.SCREEN_WIDTH;

public class GameObjectFactory {
    public static GameState createGameState() {
        BallModel ballModel = new BallModel();
        GoalModel leftGoalModel = new GoalModel(Direction.LEFT, 0, 240, 160, 20);
        GoalModel rightGoalModel = new GoalModel(Direction.RIGHT, SCREEN_WIDTH - 20, 240, 160, 20);

        return new GameState(
                ballModel,
                new PlayerModel(),
                new EnemyModel(ballModel, leftGoalModel, rightGoalModel),
                leftGoalModel,
                rightGoalModel,
                new ScoreModel()
        );
    }

    public static List<GameObjectView> createViews(GameState state) {
        return List.of(
                new BallView(state.ballModel()),
                new PlayerView(state.playerModel()),
                new EnemyView(state.enemyModel()),
                new GoalView(state.leftGoalModel()),
                new GoalView(state.rightGoalModel()),
                new ScoreView(state.scoreModel())
        );
    }

    public static List<GameMode> createGameModes(GameState state) {
        return List.of(
                new DefaultGameMode(),
                new BigGoalsGameMode(state.leftGoalModel(), state.rightGoalModel()),
                new SmallGoalsGameMode(state.leftGoalModel(), state.rightGoalModel()),
                new SmallBallGameMode(state.ballModel()),
                new BigBallGameMode(state.ballModel()),
                new BigPlayersGameMode(state.playerModel(), state.enemyModel()),
                new SmallPlayersGameMode(state.playerModel(), state.enemyModel()),
                new LightBallGameMode(state.ballModel()),
                new HeavyBallGameMode(state.ballModel()),
                new MoonGravityGameMode(state.ballModel(), state.playerModel(), state.enemyModel()),
                new PlayersHighSpeedGameMode(state.playerModel(), state.enemyModel()),
                new SlipperyFloorGameMode(state.ballModel(), state.playerModel(), state.enemyModel())
        );
    }
}

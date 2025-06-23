package ru.nsu.dmustakaev.engine;

import ru.nsu.dmustakaev.model.*;

public record GameState (
        BallModel ballModel,
        PlayerModel playerModel,
        EnemyModel enemyModel,
        GoalModel leftGoalModel,
        GoalModel rightGoalModel,
        ScoreModel scoreModel
) {
    public void reset() {
        ballModel.reset();
        playerModel.reset();
        enemyModel.reset();
    }
}

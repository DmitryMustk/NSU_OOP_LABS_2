package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.GoalModel;
import ru.nsu.dmustakaev.view.GoalView;

public class BigGoalsGameMode implements GameMode {
    private final GoalModel leftGoalModel;
    private final GoalModel rightGoalModel;

    private static final int GOALS_HEIGHT_MULTIPLIER = 2;

    public BigGoalsGameMode(GoalModel leftGoalModel, GoalModel rightGoalModel) {
        this.leftGoalModel = leftGoalModel;
        this.rightGoalModel = rightGoalModel;
    }

    private void changeGoalModel(GoalModel goalModel) {
        goalModel.setHeight(goalModel.getHeight() * GOALS_HEIGHT_MULTIPLIER);
        goalModel.setY(goalModel.getY() / GOALS_HEIGHT_MULTIPLIER);
    }

    private void restoreGoalModel(GoalModel goalModel) {
        goalModel.setHeight(goalModel.getHeight() / GOALS_HEIGHT_MULTIPLIER);
        goalModel.setY(goalModel.getY() * GOALS_HEIGHT_MULTIPLIER);
    }

    @Override
    public void apply() {
        changeGoalModel(leftGoalModel);
        changeGoalModel(rightGoalModel);
    }

    @Override
    public void unapply() {
        restoreGoalModel(leftGoalModel);
        restoreGoalModel(rightGoalModel);
    }
}

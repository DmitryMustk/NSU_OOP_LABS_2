package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.GoalModel;

public class SmallGoalsGameMode implements GameMode {
    private final GoalModel leftGoalModel;
    private final GoalModel rightGoalModel;

    private static final double GOALS_HEIGHT_MULTIPLIER = 0.5;

    private static final String SOUND_SOURCE = "/game/sounds/modes/small_goals.mp3";

    public SmallGoalsGameMode(GoalModel leftGoalModel, GoalModel rightGoalModel) {
        this.leftGoalModel = leftGoalModel;
        this.rightGoalModel = rightGoalModel;
    }

    private void changeGoalModel(GoalModel goalModel) {
        goalModel.setY(goalModel.getY() - (GOALS_HEIGHT_MULTIPLIER - 1) * goalModel.getHeight());
        goalModel.setHeight((int) (goalModel.getHeight() * GOALS_HEIGHT_MULTIPLIER));
    }

    private void restoreGoalModel(GoalModel goalModel) {
        goalModel.setHeight((int) (goalModel.getHeight() / GOALS_HEIGHT_MULTIPLIER));
        goalModel.setY(goalModel.getY() + (GOALS_HEIGHT_MULTIPLIER - 1) * goalModel.getHeight());
    }

    @Override
    public String getSoundSource() {
        return SOUND_SOURCE;
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

package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.*;

public class SlipperyFloorGameMode implements GameMode{
    private BallModel ballModel;
    private PlayerModel playerModel;
    private EnemyModel enemyModel;

    private PhysicsSimulationSettings ballModelSettings;
    private PhysicsSimulationSettings playerModelSettings;
    private PhysicsSimulationSettings enemyModelSettings;

    private static final double NEW_SOLID_RESISTANCE = -1;

    public SlipperyFloorGameMode(BallModel ballModel, PlayerModel playerModel, EnemyModel enemyModel) {
        this.ballModel = ballModel;
        this.playerModel = playerModel;
        this.enemyModel = enemyModel;
    }

    private void applySingle(PhysicalBody model, PhysicsSimulationSettings settings) {
        model.setSimulationSettings(new PhysicsSimulationSettings(
                settings.gravity(),
                settings.airResistance(),
                NEW_SOLID_RESISTANCE
        ));
    }

    @Override
    public void apply() {
        ballModelSettings = ballModel.getSimulationSettings();
        playerModelSettings = playerModel.getSimulationSettings();
        enemyModelSettings = enemyModel.getSimulationSettings();

        applySingle(ballModel, ballModelSettings);
        applySingle(playerModel, playerModelSettings);
        applySingle(enemyModel, enemyModelSettings);
    }

    @Override
    public void unapply() {
        ballModel.setSimulationSettings(ballModelSettings);
        playerModel.setSimulationSettings(playerModelSettings);
        enemyModel.setSimulationSettings(enemyModelSettings);
    }
}

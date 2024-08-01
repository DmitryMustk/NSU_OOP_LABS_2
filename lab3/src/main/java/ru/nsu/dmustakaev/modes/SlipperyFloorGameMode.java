package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.*;

public class SlipperyFloorGameMode implements GameMode{
    private final BallModel ballModel;
    private final PlayerModel playerModel;
    private final EnemyModel enemyModel;

    private PhysicsSimulationSettings ballModelSettings;
    private PhysicsSimulationSettings playerModelSettings;
    private PhysicsSimulationSettings enemyModelSettings;

    private static final double NEW_SOLID_RESISTANCE = -1;

    private static final String SOUND_SOURCE = "/game/sounds/modes/slippery_floor.mp3";

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
    public String getSoundSource() {
        return SOUND_SOURCE;
    }

    @Override
    public String getTitle() {
        return "Slippery Floor";
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

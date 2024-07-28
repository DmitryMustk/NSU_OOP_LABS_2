package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.BallModel;
import ru.nsu.dmustakaev.model.EnemyModel;
import ru.nsu.dmustakaev.model.PhysicsSimulationSettings;
import ru.nsu.dmustakaev.model.PlayerModel;

public class MoonGravityGameMode implements GameMode {
    private final BallModel ballModel;
    private final PlayerModel playerModel;
    private final EnemyModel enemyModel;

    private PhysicsSimulationSettings ballModelSettings;
    private PhysicsSimulationSettings playerModelSettings;
    private PhysicsSimulationSettings enemyModelSettings;

    private static final double GRAvITY_MULTIPLIER = 0.5;

    private static final String SOUND_SOURCE = "/game/sounds/modes/moon_gravity.mp3";

    public MoonGravityGameMode(BallModel ballModel, PlayerModel playerModel, EnemyModel enemyModel) {
        this.ballModel = ballModel;
        this.playerModel = playerModel;
        this.enemyModel = enemyModel;
    }

    @Override
    public String getSoundSource() {
        return SOUND_SOURCE;
    }

    @Override
    public void apply() {
        ballModelSettings = ballModel.getSimulationSettings();
        playerModelSettings = playerModel.getSimulationSettings();
        enemyModelSettings = enemyModel.getSimulationSettings();

        ballModel.setSimulationSettings(new PhysicsSimulationSettings(
                ballModelSettings.gravity() * GRAvITY_MULTIPLIER,
                ballModelSettings.airResistance(),
                ballModelSettings.solidResistance()
        ));

        playerModel.setSimulationSettings(new PhysicsSimulationSettings(
                playerModelSettings.gravity() * GRAvITY_MULTIPLIER,
                playerModelSettings.airResistance(),
                playerModelSettings.solidResistance()
        ));

        enemyModel.setSimulationSettings(new PhysicsSimulationSettings(
                enemyModelSettings.gravity() * GRAvITY_MULTIPLIER,
                enemyModelSettings.airResistance(),
                enemyModelSettings.solidResistance()
        ));
    }

    @Override
    public void unapply() {
        ballModel.setSimulationSettings(ballModelSettings);
        playerModel.setSimulationSettings(playerModelSettings);
        enemyModel.setSimulationSettings(enemyModelSettings);
    }
}

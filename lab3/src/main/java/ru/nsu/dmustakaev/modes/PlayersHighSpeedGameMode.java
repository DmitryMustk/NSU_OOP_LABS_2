package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.EnemyModel;
import ru.nsu.dmustakaev.model.PlayerModel;

public class PlayersHighSpeedGameMode implements GameMode{
    private final PlayerModel playerModel;
    private final EnemyModel enemyModel;

    private static final double MOVEMENT_SPEED_MULTIPLIER = 1.6;

    private static final String SOUND_SOURCE = "/game/sounds/modes/players_high_speed.mp3";

    public PlayersHighSpeedGameMode(PlayerModel playerModel, EnemyModel enemyModel) {
        this.playerModel = playerModel;
        this.enemyModel = enemyModel;
    }

    @Override
    public String getSoundSource() {
        return SOUND_SOURCE;
    }

    @Override
    public String getTitle() {
        return "High Speed";
    }

    @Override
    public void apply() {
        playerModel.setMovementSpeed(playerModel.getMovementSpeed() * MOVEMENT_SPEED_MULTIPLIER);
        playerModel.getMaxSpeed().mulVecOnScalar(MOVEMENT_SPEED_MULTIPLIER);
        enemyModel.setMovementSpeed(enemyModel.getMovementSpeed() * MOVEMENT_SPEED_MULTIPLIER);
        enemyModel.getMaxSpeed().mulVecOnScalar(MOVEMENT_SPEED_MULTIPLIER);
    }

    @Override
    public void unapply() {
        playerModel.setMovementSpeed(playerModel.getMovementSpeed() / MOVEMENT_SPEED_MULTIPLIER);
        playerModel.getMaxSpeed().mulVecOnScalar(1 / MOVEMENT_SPEED_MULTIPLIER);
        enemyModel.setMovementSpeed(enemyModel.getMovementSpeed() / MOVEMENT_SPEED_MULTIPLIER);
        enemyModel.getMaxSpeed().mulVecOnScalar(1 / MOVEMENT_SPEED_MULTIPLIER);
    }
}

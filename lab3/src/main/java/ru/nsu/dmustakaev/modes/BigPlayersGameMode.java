package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.EnemyModel;
import ru.nsu.dmustakaev.model.PlayerModel;

public class BigPlayersGameMode implements GameMode {
    private PlayerModel playerModel;
    private EnemyModel enemyModel;

    private static final int PLAYERS_RADIUS_MULTIPLIER = 2;

    public BigPlayersGameMode(PlayerModel playerModel, EnemyModel enemyModel) {
        this.playerModel = playerModel;
        this.enemyModel = enemyModel;
    }

    @Override
    public void apply() {
        playerModel.setRadius(playerModel.getRadius() * PLAYERS_RADIUS_MULTIPLIER);
        enemyModel.setRadius(enemyModel.getRadius() * PLAYERS_RADIUS_MULTIPLIER);
    }

    @Override
    public void unapply() {
        playerModel.setRadius(playerModel.getRadius() / PLAYERS_RADIUS_MULTIPLIER);
        enemyModel.setRadius(enemyModel.getRadius() / PLAYERS_RADIUS_MULTIPLIER);
    }
}

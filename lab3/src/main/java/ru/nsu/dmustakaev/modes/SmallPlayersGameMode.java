package ru.nsu.dmustakaev.modes;

import ru.nsu.dmustakaev.model.EnemyModel;
import ru.nsu.dmustakaev.model.PlayerModel;

public class SmallPlayersGameMode implements GameMode {
    private PlayerModel playerModel;
    private EnemyModel enemyModel;

    private static final double PLAYERS_RADIUS_MULTIPLIER = 0.5;

    private static final String SOUND_SOURCE = "/game/sounds/modes/small_players.mp3";

    public SmallPlayersGameMode(PlayerModel playerModel, EnemyModel enemyModel) {
        this.playerModel = playerModel;
        this.enemyModel = enemyModel;
    }

    @Override
    public String getSoundSource() {
        return SOUND_SOURCE;
    }

    @Override
    public String getTitle() {
        return "Small Players";
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

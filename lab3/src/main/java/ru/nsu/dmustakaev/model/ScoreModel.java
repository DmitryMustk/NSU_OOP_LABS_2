package ru.nsu.dmustakaev.model;

public class ScoreModel {
    private int playerScore;
    private int enemyScore;

    public ScoreModel() {
        playerScore = 0;
        enemyScore = 0;
    }

    public void incrementPlayerScore() {
        playerScore++;
    }

    public void incrementEnemyScore() {
        enemyScore++;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getEnemyScore() {
        return enemyScore;
    }
}

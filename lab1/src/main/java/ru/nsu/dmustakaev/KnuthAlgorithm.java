package ru.nsu.dmustakaev;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnuthAlgorithm {
    private final int DIGITS = 4;
    private final int attemptsNumber = 0;
    private final List<String> ALL_CODES;
    private List<String> S;
    private String guess;

    public KnuthAlgorithm(){
        this.ALL_CODES = generateAllCodes();
        this.S = new ArrayList<>(ALL_CODES);
        this.guess = "1122";
    }

    private List<String> generateAllCodes() {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    for (int l = 0; l <= 9; l++) {
                        codes.add("" + i + j + k + l);
                    }
                }
            }
        }
        return codes;
    }

    private int[] computeBullsAndCows(String code, String guess) {
        int[] bullsAndCows = new int[2];
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < DIGITS; i++) {
            if (guess.charAt(i) == code.charAt(i)) {
                bulls++;
            } else if (code.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }
        bullsAndCows[0] = bulls;
        bullsAndCows[1] = cows;
        return bullsAndCows;
    }

    public String getNextGuess(int[] bullsAndCows) {
        int bulls = bullsAndCows[0];
        int cows = bullsAndCows[1];

        List<String> codesToRemove = new ArrayList<>();
        for(String code : S){
            int[] bullsAndCowsToRemove = computeBullsAndCows(code, guess);
            if(bullsAndCowsToRemove[0] != bulls || bullsAndCowsToRemove[1] != cows){
                codesToRemove.add(code);
            }
        }
        S.removeAll(codesToRemove);
        guess = minimaxChooseNextGuess();
        return guess;
    }

    private String minimaxChooseNextGuess(){
        int minMaxScore = Integer.MAX_VALUE;
        String nextGuess = "";
        for (String code : ALL_CODES) {
            int[] scores = new int[S.size() + 1];
            Arrays.fill(scores, 0);

            for (String s : S) {
                int[] bullsAndCows = computeBullsAndCows(code, s);
                int bulls = bullsAndCows[0];
                int cows = bullsAndCows[1];
                int score = Math.max(cows, bulls);
                scores[score]++;
            }

            int maxScore = Arrays.stream(scores).max().orElse(0);
            if (maxScore < minMaxScore) {
                minMaxScore = maxScore;
                nextGuess = code;
            }
        }
        return nextGuess;
    }
}

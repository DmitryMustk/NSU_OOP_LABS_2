package ru.nsu.dmustakaev;

public class BullAndCowsGame {
    protected static int DIGITS = 4;
    protected static int ALPHABET_SIZE = 10;
    protected UserInterface ui;
    protected String wishedNumber;

    protected int[] computeBullsAndCows(String guess){
        int bulls = 0;
        int cows = 0;
        for(int i = 0; i < PlayerGuessNumberGame.DIGITS; ++i) {
            if(wishedNumber.charAt(i) == guess.charAt(i)){
                bulls++;
            }else if(wishedNumber.contains(String.valueOf(guess.charAt(i)))){
                cows++;
            }
        }
        int[] bullsAndCows = new int[2];
        bullsAndCows[0] = bulls;
        bullsAndCows[1] = cows;
        return bullsAndCows;
    }

    protected int get_Digits(){
        return DIGITS;
    }

    protected int getAlphabetSize(){
        return ALPHABET_SIZE;
    }
    protected boolean isNotValidGuess(String guess){
        return guess.matches("[0-9]{" + PlayerGuessNumberGame.DIGITS + "}")
                && guess.chars().distinct().count() == 4;
    }

}

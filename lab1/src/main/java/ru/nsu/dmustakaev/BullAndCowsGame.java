package ru.nsu.dmustakaev;

public class BullAndCowsGame {
    protected static int DIGITS = 4;

    protected UserInterface ui;
    protected String wishedNumber;

    protected String computeBullsAndCows(String guess){
        int bulls = 0;
        int cows = 0;
        for(int i = 0; i < PlayerGuessNumberGame.DIGITS; ++i) {
            if(wishedNumber.charAt(i) == guess.charAt(i)){
                bulls++;
            }else if(wishedNumber.contains(String.valueOf(guess.charAt(i)))){
                cows++;
            }
        }
        return "\n-быков: " + bulls + "\n-коров: " + cows + "\n";
    }

    protected boolean isValidGuess(String guess){
        return guess.matches("[0-9]{" + PlayerGuessNumberGame.DIGITS + "}")
                && guess.chars().distinct().count() == 4;
    }

}

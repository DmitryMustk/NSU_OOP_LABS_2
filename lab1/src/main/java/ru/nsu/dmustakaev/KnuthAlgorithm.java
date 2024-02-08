package ru.nsu.dmustakaev;

import java.util.ArrayList;
import java.util.List;

public class KnuthAlgorithm {
    private int alphabetSize;
    private int numberOfDigits;
    private int numberOfCodes;
    private ArrayList<String> codes;

    private String lastGuess;

    KnuthAlgorithm(ComputerGuessNumberGame computerGuessNumberGame) {
        alphabetSize = computerGuessNumberGame.getAlphabetSize();
        numberOfDigits = computerGuessNumberGame.get_Digits();
        numberOfCodes = (int) Math.pow(alphabetSize, numberOfDigits);
        codes = new ArrayList<String>();
        fillCodes();
    }

    public String getFirstGuess(){
        lastGuess = "1234";
        return lastGuess;
    }
    public String getNextGuess(int[] bullsAndCows){
        removeBadCodes(bullsAndCows);
//        System.out.println(codes);

        for(String code: codes){
            if(code.chars().distinct().count() == 4){
                lastGuess = code;
                return code;
            }
        }

        return codes.get(0);
    }
    private void fillCodes(){
        for (int i = 0; i < alphabetSize; ++i){
            for(int j = 0; j < alphabetSize; ++j){
                for(int k = 0; k < alphabetSize; ++k){
                    for(int l = 0; l < alphabetSize; ++l){
                        codes.add("" + i + j + k + l);
                    }
                }
            }
        }
    }

    private void removeBadCodes(int[] bullsAndCows){
        List<String> codesToRemove = new ArrayList<String>();
        for(String code : codes){
            if(isBadCode(code, lastGuess, bullsAndCows)){
                codesToRemove.add(code);
            }
        }
        codes.removeAll(codesToRemove);
    }

    private boolean isBadCode(String code, String guess, int[] bullsAndCows){
        return checkBulls(code, guess, bullsAndCows[0]) && checkCows(code, guess, bullsAndCows[1]);
    }

    private boolean checkCows(String code, String guess, int cows){
        int codeCows = 0;
        for(int i = 0; i < numberOfDigits; ++i){
            if(code.charAt(i) != guess.charAt(i) && new String(guess).indexOf(code.charAt(i)) != -1){
                codeCows++;
            }
        }
        return  codeCows >= cows;
    }
    private boolean checkBulls(String code, String guess, int bulls){
        int codeBulls = 0;
        for(int i = 0; i < numberOfDigits; ++i){
            if (code.charAt(i) == guess.charAt(i)){
                codeBulls++;
            }
        }
        return codeBulls == bulls;

    }
}

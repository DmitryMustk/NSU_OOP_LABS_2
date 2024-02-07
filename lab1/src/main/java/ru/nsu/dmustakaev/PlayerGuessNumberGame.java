package ru.nsu.dmustakaev;

public class PlayerGuessNumberGame implements Game {
    private static int DIGITS = 4;

    private UserInterface ui;
    private String wishedNumber;

    public PlayerGuessNumberGame(UserInterface ui) {
        this.ui = ui;
        ui.showWelcomeMessage();
    }

    public void start() {
        ui.showMessage("Попробуйте угадать 4-значное число, состоящее из уникальных цифр");
        wishedNumber = generateRandomNumber();
        int attemptCount = 1;
        while (true) {
            ui.showMessage("Введите вашу догадку: ");
            String userGuess = ui.getUserInput();

            if(!isValidGuess(userGuess)){
                ui.showMessage("Неверный формат ответа. Введите 4-значное число с уникальными цифрами");
                continue;
            }

            if(!userGuess.equals(wishedNumber)){
                ui.showMessage("Пока не правильно:" + computeBullsAndCows(userGuess) + "Попробуйте еще раз!");
            }else{
                ui.showMessage("Поздравляю, вы отгадали число за " + attemptCount + " попыток!");
                break;
            }

            attemptCount++;
        }
    }

    private String computeBullsAndCows(String guess){
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

    private boolean isValidGuess(String guess){
        return guess.matches("[0-9]{" + PlayerGuessNumberGame.DIGITS + "}")
                && guess.chars().distinct().count() == 4;
    }

    private String generateRandomNumber() {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 4) {
            int digit = (int) (Math.random() * 10);
            if (sb.indexOf(String.valueOf(digit)) == -1) {
                sb.append(digit);
            }
        }
        return sb.toString();
    }
}

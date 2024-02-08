package ru.nsu.dmustakaev;

public class PlayerGuessNumberGame extends BullAndCowsGame implements Game{

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

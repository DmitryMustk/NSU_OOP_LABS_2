package ru.nsu.dmustakaev;

public class ComputerGuessNumberGame extends BullAndCowsGame implements Game{
    KnuthAlgorithm knuthAlgorithm = new KnuthAlgorithm(this);

    public ComputerGuessNumberGame(UserInterface ui) {
        this.ui = ui;
        ui.showWelcomeMessage();
    }

    public void start(){
        ui.showMessage("Загадайте 4-значное число с уникальными цифрами а компьютер постарается его угадать");
        wishedNumber = getWishedNumberFromUser();

        String guess = knuthAlgorithm.getFirstGuess();
        ui.showMessage(guess);
        if(wishedNumber.equals(guess)){
            return;
        }
        while (true){
            guess = knuthAlgorithm.getNextGuess(computeBullsAndCows(guess));
            ui.showMessage(guess);
            if(wishedNumber.equals(guess)){
                return;
            }
        }
    }
    private String getWishedNumberFromUser(){
        while (true) {
            String userNumber = ui.getUserInput();
            if(isNotValidGuess(userNumber)) {
                ui.showMessage("Неверный формат числа, попробуйте еще раз");
                continue;
            }
            return userNumber;
        }
    }
}

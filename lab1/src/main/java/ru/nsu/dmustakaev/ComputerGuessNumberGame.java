package ru.nsu.dmustakaev;

public class ComputerGuessNumberGame extends BullAndCowsGame implements Game{
    KnuthAlgorithm knuthAlgorithm = new KnuthAlgorithm();

    public ComputerGuessNumberGame(UserInterface ui) {
        this.ui = ui;
        ui.showWelcomeMessage();
    }

    @Override
    public void start(){
        ui.showMessage("Загадайте 4-значное число с уникальными цифрами а компьютер постарается его угадать");
        wishedNumber = getWishedNumberFromUser();


    }

    private String getWishedNumberFromUser(){
        while (true) {
            String userNumber = ui.getUserInput();
            if(!isValidGuess(userNumber)) {
                ui.showMessage("Неверный формат числа, попробуйте еще раз");
                continue;
            }
            return userNumber;
        }
    }
}

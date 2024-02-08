package ru.nsu.dmustakaev;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface(scanner);
        ui.showWelcomeMessage();

        int choise = ui.askGameMode();
        GameFactory gameFactory = new GameFactory(ui);
        Game game = gameFactory.createGame(choise);
        if (game != null){
            game.start();
        } else {
            ui.showErrorMessage("Неверный выбор");
        }
    }
}

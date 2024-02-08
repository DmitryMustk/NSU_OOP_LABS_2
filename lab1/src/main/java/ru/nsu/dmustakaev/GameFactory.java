package ru.nsu.dmustakaev;

public class GameFactory {
        private UserInterface ui;

        public GameFactory(UserInterface ui) {
            this.ui = ui;
        }
        public Game createGame(int choice) {
            switch (choice) {
                case 1:
                    return new PlayerGuessNumberGame(ui);
                case 2:
                    return new ComputerGuessNumberGame(ui);
                default:
                    return null;
            }
        }
}

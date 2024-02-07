package ru.nsu.dmustakaev;

import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showWelcomeMessage() {
        System.out.println("Добро пожаловать в игру \"Быки и коровы!\"");
    }

    public int askGameMode() {
        System.out.println("Выберете режим игры:");
        System.out.println("1. Компьютер загадывает число, вы пытаетесь его угадать");
        System.out.println("2. Вы загадываете число, компьютер пытается его угадать.");
        return scanner.nextInt();
    }

    public void showErrorMessage(String message) {
        System.out.println(message);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String getUserInput() {
        return scanner.next();
    }
}

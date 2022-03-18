package hu.progmasters.webshop.handlers;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {

    private final Scanner scanner = new Scanner(System.in);

    public int getInputNumber() {
        int option = -1;
        do {
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                OutputHandler.outputRed("It's not a number");
                scanner.nextLine();
            }
        } while (option < 0);
        scanner.nextLine();
        return option;
    }

    public String getInputString() {
        String data;
        do {
            data = scanner.nextLine();

        } while (data == null);
        return data;
    }

    public double getInputDouble() {
        double option = -1;
        do {
            try {
                option = scanner.nextDouble();
            } catch (InputMismatchException e) {
                OutputHandler.outputRed("It's not a number");
                scanner.nextLine();
            }
        } while (option < 0);
        scanner.nextLine();
        return option;
    }
}

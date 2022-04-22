package hu.progmasters.webshop.handlers;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {

    private static final Scanner SCANNER = new Scanner(System.in);

    private InputHandler() {
    }

    public static int getInputNumber() {
        int option = -1;
        do {
            try {
                option = SCANNER.nextInt();
            } catch (InputMismatchException e) {
                OutputHandler.outputRed("It's not a number");
                SCANNER.nextLine();
            }
        } while (option < 0);
        SCANNER.nextLine();
        return option;
    }

    public static long getInputLong() {
        long option = -1;
        do {
            try {
                option = SCANNER.nextLong();
            } catch (InputMismatchException e) {
                OutputHandler.outputRed("It's not a number");
                SCANNER.nextLine();
            }
        } while (option < 0);
        SCANNER.nextLine();
        return option;
    }

    public static String getInputString() {
        String data;
        do {
            data = SCANNER.nextLine();

        } while (data == null);
        return data;
    }

    public static double getInputDouble() {
        double option = -1;
        do {
            try {
                option = SCANNER.nextDouble();
            } catch (InputMismatchException e) {
                OutputHandler.outputRed("It's not a number");
                SCANNER.nextLine();
            }
        } while (option < 0);
        SCANNER.nextLine();
        return option;
    }
}

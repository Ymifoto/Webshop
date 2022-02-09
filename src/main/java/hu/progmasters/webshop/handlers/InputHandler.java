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
                System.out.println("Its not int");
                scanner.nextLine();
            }
        } while (option < 0);
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
                System.out.println("Its not a double");
                scanner.nextLine();
            }
        } while (option < 0);
        return option;
    }
}

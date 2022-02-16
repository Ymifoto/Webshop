package hu.progmasters.webshop.handlers;

public class OutputHandler {

    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String CYAN = "\033[0;36m";
    private static final String BLUE = "\033[0;34m";
    private static final String RESET = "\033[0m";

    public static void outputRed(String text) {
        System.out.println(RED + text + RESET);
    }

    public static void outputYellow(String text) {
        System.out.println(YELLOW + text + RESET);
    }

    public static void outputGreen(String text) {
        System.out.println(GREEN + text + RESET);
    }

    public static void outputCyan(String text) {
        System.out.println(CYAN + text + RESET);
    }

    public static void outputBlue(String text) {
        System.out.println(BLUE + text + RESET);
    }
}

package hu.progmasters.webshop.handlers;

import java.util.List;
import java.util.Map;

public class OutputHandler {

    private static final int VERTICAL_LINE_DIFFERENT = 7;
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

    public static void printList(List<String> data, String head) {
        int longestValue = Math.max(head.length(), data.stream().mapToInt(String::length).max().getAsInt());
        System.out.println("-".repeat(longestValue + VERTICAL_LINE_DIFFERENT));
        System.out.println("| " + head + " ".repeat(longestValue - head.length()) + " |");
        System.out.println("-".repeat(longestValue + VERTICAL_LINE_DIFFERENT));
        data.forEach(value -> System.out.println("| " + value + " ".repeat(longestValue - value.length()) + " |"));
        System.out.println("-".repeat(longestValue + VERTICAL_LINE_DIFFERENT));

    }

    public static void printMap(Map<String, String> data, String firstColumn, String secondColumn) {
        int longestKeyWidth;
        int longestValueWidth;

        if (data.size() > 0) {
            longestKeyWidth = Math.max(firstColumn.length(),
                    data.keySet().stream().mapToInt(String::length).max().getAsInt());
            longestValueWidth = Math.max(secondColumn.length(),
                    data.values().stream().mapToInt(String::length).max().getAsInt());

            System.out.println("-".repeat(longestKeyWidth + longestValueWidth + VERTICAL_LINE_DIFFERENT));
            System.out.println("| " + firstColumn + " ".repeat(longestKeyWidth - firstColumn.length()) + " | " + secondColumn + " ".repeat(longestValueWidth - secondColumn.length()) + " |");
            System.out.println("-".repeat(longestKeyWidth + longestValueWidth + VERTICAL_LINE_DIFFERENT));
            data.forEach((key, value) -> System.out.println("| " + key + " ".repeat(longestKeyWidth - key.length()) + " | " + value + " ".repeat(longestValueWidth - value.length()) + " |"));
            System.out.println("-".repeat(longestKeyWidth + longestValueWidth + VERTICAL_LINE_DIFFERENT));
        }
    }
}

package hu.progmasters.webshop.handlers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogHandler {

    private static final List<String> EVENTS = new ArrayList<>();
    private static final String FILE = "src/main/java/hu/progmasters/webshop/log/webshop.txt";

    static {
        create();
    }

    public static void writeLog() {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILE), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            for (String event : EVENTS) {
                writer.write(event);
                writer.newLine();
            }
            EVENTS.clear();
        } catch (IOException e) {
            System.out.println("CAN'T WRITE LOG FILE!");
        }
    }

    public static void addLog(String event) {
        EVENTS.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + event);
        if (EVENTS.size() >= 100) {
            writeLog();
        }
    }

    private static void create() {
        Path file = Path.of(FILE);
        try {
            if (Files.deleteIfExists(file)) {
                Files.createFile(file);
            }
        } catch (IOException e) {
            System.out.println("CAN CREATE FILE!");
        }
    }
}

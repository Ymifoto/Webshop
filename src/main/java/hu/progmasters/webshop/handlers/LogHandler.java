package hu.progmasters.webshop.handlers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogHandler {

    private static final String FILE = "src/main/java/hu/progmasters/webshop/log/webshop.txt";

    static {
        create();
    }

    public static void addLog(String event) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILE), StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
                writer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - " + event);
                writer.newLine();
        } catch (IOException e) {
            System.out.println("CAN'T WRITE LOG FILE!");
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

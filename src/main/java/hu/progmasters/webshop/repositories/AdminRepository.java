package hu.progmasters.webshop.repositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class AdminRepository extends Repository {

    private static final String SAMPLE_DATA_FILE = "src/main/resources/webshop_sampledata.csv";
    private static final String TEST_DATA_FILE = "src/main/resources/webshop_testdata.csv";

    public void loadData() {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(SAMPLE_DATA_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                execute(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTestData() {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(TEST_DATA_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                execute(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dropTables() {
        String tableNames = List.of(Tables.values()).stream().map(Tables::name).collect(Collectors.joining(","));
        execute("DROP TABLES " + tableNames);
    }

    public void createTables() {
        for (Tables value : Tables.values()) {
            execute(value.getSql());
        }
    }
}

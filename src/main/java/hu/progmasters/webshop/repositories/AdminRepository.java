package hu.progmasters.webshop.repositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class AdminRepository extends Repository {

    private static final AdminRepository ADMIN_REPOSITORY = new AdminRepository();
    private static final String SAMPLE_DATA_FILE = "src/main/resources/webshop_sampledata.csv";
    private static final String TEST_DATA_FILE = "src/main/resources/webshop_testdata.csv";

    private AdminRepository() {
    }

    public static AdminRepository getRepository() {
        return ADMIN_REPOSITORY;
    }

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

    public void deleteData() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.addBatch("SET FOREIGN_KEY_CHECKS = 0");
            for (Tables value : Tables.values()) {
                statement.addBatch("TRUNCATE TABLE " + value.name() + ";");
            }
            statement.addBatch("SET FOREIGN_KEY_CHECKS = 1");
            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        for (Tables value : Tables.values()) {
            execute(value.getSql());
        }
    }
}

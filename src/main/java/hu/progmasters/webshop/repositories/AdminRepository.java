package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminRepository extends Repository {

    private static final String DATA_FILE = "src/main/resources/webshop_basedata.csv";

    public void loadData() {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(DATA_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                execute(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteData() {
        try (Connection connection = DatabaseConfig.getConnection();
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

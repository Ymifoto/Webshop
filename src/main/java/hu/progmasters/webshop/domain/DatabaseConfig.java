package hu.progmasters.webshop.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String DB_URL = "jdbc:mysql://localhost/webshop_team6";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Test123!";

    private static final String TEST_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MySQL";
    private static final String TEST_USER = "sa";
    private static final String TEST_PASSWORD = "sa";

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static Connection getTestConnection() throws SQLException {
        return DriverManager.getConnection(TEST_URL, TEST_USER, TEST_PASSWORD);
    }
}

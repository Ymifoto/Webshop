package hu.progmasters.webshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    public static final String DB_URL = "jdbc:mysql://localhost/webshop_team6?serverTimezone=UTC&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "Test123!";

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

}

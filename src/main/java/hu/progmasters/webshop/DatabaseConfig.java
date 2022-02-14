package hu.progmasters.webshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

//    private static final String DB_URL = "jdbc:mysql://sql11.freemysqlhosting.net/sql11472846";
//    private static final String USER = "sql11472846";
//    private static final String PASSWORD = "qTgiGNYJEg";

    private static final String DB_URL = "jdbc:mysql://localhost/webshop_team6";
    private static final String USER = "root";
    private static final String PASSWORD = "Test123!";

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

}

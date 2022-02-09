package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface Repository {

    default void execute(String sql) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

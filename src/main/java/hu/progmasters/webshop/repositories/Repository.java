package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.handlers.OutputHandler;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Repository {

    protected void execute(String sql) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            OutputHandler.outputRed("Execute error!" + e.getMessage());
        }
    }

    protected void update(String table, int id, Map<String, String> data) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "UPDATE " + table + " SET " + data.keySet().stream().collect(Collectors.joining(" = ?,")) + " = ? WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setMapValues(preparedStatement, new LinkedList<>(data.values()));
            preparedStatement.setInt(data.size() + 1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            OutputHandler.outputRed("Update error!" + e.getMessage());
        }
        LogHandler.addLog("Update " + table + " table, updated id: " + id + ", " + data.keySet());
    }

    protected int insert(String table, Map<String, String> datas) {
        int id = -1;
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "INSERT INTO " + table + "(" + datas.keySet().stream().collect(Collectors.joining(", ")) + ") VALUES(" + getPlaceHolders(datas.size()) + ");";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            setMapValues(preparedStatement, new LinkedList<>(datas.values()));
            preparedStatement.execute();
            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            OutputHandler.outputRed("Insert error!" + e.getMessage());
            return id;
        }
        LogHandler.addLog("Insert " + table + " table, ID: " + id);
        return id;
    }

    private String getPlaceHolders(int count) {
        String placeHolders = "";
        for (int i = 0; i < count; i++) {
            placeHolders += i + 1 == count ? "?" : "?,";
        }
        return placeHolders;
    }

    private void setMapValues(PreparedStatement preparedStatement, List<String> datas) throws SQLException {
        int counter = 1;
        for (String data : datas) {
            preparedStatement.setString(counter, data);
            counter++;
        }
    }

    protected void updateCategoriesTable() {
        String sql = "INSERT IGNORE INTO categories(product_id,category_id) SELECT id,category_id FROM products WHERE category_id IS NOT NULL;";
        execute(sql);
    }
}

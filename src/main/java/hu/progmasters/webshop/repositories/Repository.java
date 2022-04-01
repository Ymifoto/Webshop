package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.domain.DatabaseConfig;
import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.handlers.OutputHandler;

import java.sql.*;
import java.util.Map;

public abstract class Repository {

    private static boolean testMode = false;

    protected void execute(String sql) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            OutputHandler.outputRed("Execute error!" + e.getMessage());
        }
    }

    protected void update(String table, int id, Map<String, String> datas) {
        String sql = "UPDATE " + table + " SET " + String.join(" = ?,", datas.keySet()) + " = ? WHERE id = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setMapValues(preparedStatement, datas);
            preparedStatement.setInt(datas.size() + 1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            OutputHandler.outputRed("Update error!" + e.getMessage());
        }
        LogHandler.addLog("Update " + table + " table, updated id: " + id + ", " + datas.keySet());
    }

    protected int insert(String table, Map<String, String> datas) {
        int id = -1;
        String sql = "INSERT INTO " + table + "(" + String.join(", ", datas.keySet()) + ") VALUES(" + getPlaceHolders(datas.size()) + ");";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            setMapValues(preparedStatement, datas);
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

    private void setMapValues(PreparedStatement preparedStatement, Map<String, String> datas) throws SQLException {
        int counter = 1;
        for (Map.Entry<String, String> entry : datas.entrySet()) {
            preparedStatement.setString(counter, entry.getValue());
            counter++;
        }
    }

    protected void updateCategoriesTable() {
        String sql = "DELETE FROM categories WHERE id > 0;" +
                "INSERT INTO categories(product_id,category_id) SELECT id,category_id FROM products WHERE category_id IS NOT NULL;";
        execute(sql);
    }

    protected Connection getConnection() throws SQLException {
        if (testMode) {
            return DatabaseConfig.getTestConnection();
        }
        return DatabaseConfig.getConnection();
    }

    public static void setTestMode(boolean testMode) {
        Repository.testMode = testMode;
    }

    public static boolean isTestMode() {
        return testMode;
    }
}

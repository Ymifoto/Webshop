package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.domain.DatabaseConfig;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.handlers.OutputHandler;

import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

public abstract class Repository {

    private static boolean testMode = false;
    private static boolean testDatabaseCreated = false;

    protected void execute(String sql) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            OutputHandler.outputRed("Execute error!" + e.getMessage());
        }
    }

    protected void update(String table, int id, Map<String, Object> datas) {
        String sql = "UPDATE " + table + " SET " + String.join(" = ?,", datas.keySet()) + " = ? WHERE id = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setMapValues(preparedStatement, datas);
            preparedStatement.setInt(datas.size() + 1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            OutputHandler.outputRed("Update error!" + e.getMessage());
        }
        LogHandler.addLog("Update " + table + " table, updated id: " + id + ", " + datas.keySet());
    }

    protected int insert(String table, Map<String, Object> datas) {
        int id = -1;
        String sql = "INSERT INTO " + table + "(" + String.join(", ", datas.keySet()) + ") VALUES(" + getPlaceHolders(datas.size()) + ");";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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

    protected void updateCategoriesTable() {
        String sqlDelete = "DELETE FROM categories WHERE id > 0; ";
        execute(sqlDelete);
        String sql = "INSERT INTO categories(product_id,category_id) SELECT id,category_id FROM products WHERE category_id IS NOT NULL;";
        execute(sql);
    }

    protected Connection getConnection() throws SQLException {
        if (testMode) {
            return DatabaseConfig.getTestConnection();
        }
        return DatabaseConfig.getConnection();
    }

    protected Product createProduct(ResultSet result) throws SQLException {
        ResultSetMetaData resultData = result.getMetaData();
        Map<String, Object> data = new TreeMap<>();

        for (int i = 1; i <= resultData.getColumnCount(); i++) {
            data.put(resultData.getColumnLabel(i), result.getObject(i));
        }
        return new Product().updateData(data);
    }

    public static void setTestMode(boolean testMode) {
        Repository.testMode = testMode;
    }

    public static void setTestDatabaseCreated(boolean testDatabaseCreated) {
        Repository.testDatabaseCreated = testDatabaseCreated;
    }

    public static boolean isTestDatabaseCreated() {
        return testDatabaseCreated;
    }

    private String getPlaceHolders(int count) {
        String placeHolders = "";
        for (int i = 0; i < count; i++) {
            placeHolders += i + 1 == count ? "?" : "?,";
        }
        return placeHolders;
    }

    private void setMapValues(PreparedStatement preparedStatement, Map<String, Object> datas) throws SQLException {
        int counter = 1;
        for (Map.Entry<String, Object> entry : datas.entrySet()) {
            preparedStatement.setString(counter, (String) entry.getValue());
            counter++;
        }
    }
}

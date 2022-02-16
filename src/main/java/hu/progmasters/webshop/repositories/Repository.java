package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.handlers.LogHandler;
import hu.progmasters.webshop.handlers.OutputHandler;

import java.sql.*;
import java.util.Map;

public abstract class Repository {

    protected void execute(String sql) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            OutputHandler.outputRed("Execute error!");
        }
    }

    protected void update(String table, int id, Map<String, String> data) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "UPDATE " + table + " SET " + getColumsName(true, data) + " WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setMapValues(preparedStatement, data);
            preparedStatement.setInt(data.size() + 1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            OutputHandler.outputRed("Update error!");
        }
        LogHandler.addLog("Update " + table + " table, updated id: " + id + ", " + data.keySet());
    }

    protected int insert(String table, Map<String, String> datas) {
        int id = -1;
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "INSERT INTO " + table + "(" + getColumsName(false,datas) + ") VALUES(" + getPlaceHolders(datas.size()) + ");";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            setMapValues(preparedStatement, datas);
            preparedStatement.execute();
            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException e) {
            OutputHandler.outputRed("Insert error!");
            return id;
        }
        LogHandler.addLog("Insert " + table + " table, ID: " + id);
        return id;
    }

    private String getColumsName(boolean update, Map<String, String> datas) {
        String setColumns = "";
        int counter = 1;
        for (Map.Entry<String, String> entry : datas.entrySet()) {
            if (update) {
                setColumns = counter != datas.size() ? setColumns + entry.getKey() + " = ?," : setColumns + entry.getKey() + " = ?";
            } else {
                setColumns = counter != datas.size() ? setColumns + entry.getKey() + "," : setColumns + entry.getKey();
            }
            counter++;
        }
        return setColumns;
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
        String sql = "INSERT IGNORE INTO categories(product_id,category_id) SELECT id,category_id FROM products WHERE category_id IS NOT NULL;";
        execute(sql);
    }
}

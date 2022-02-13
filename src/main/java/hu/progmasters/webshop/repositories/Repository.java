package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.handlers.LogHandler;

import java.sql.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class Repository {

    protected void execute(String sql) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void update(String table, int id, Map<String,String> data) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "UPDATE " + table + " SET " + getColumsNameForUpdate(data.keySet()) + " WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            setMapValues(preparedStatement, data.values());
            preparedStatement.setInt(data.size() + 1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Update error!");
        }
        System.out.println("Update succes");
        LogHandler.addLog("Update " + table + " table, updated id: " + id + ", " + data.keySet());
    }

    protected int insert(String table, Map<String, String> datas) {
        int id = -1;
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "INSERT INTO " + table + "(" + getColumsNameForInsert(datas.keySet()) + ") VALUES(" + getPlaceHolders(datas.size()) + ");";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            setMapValues(preparedStatement, datas.values());
            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Insert error!");
        }
        System.out.println("Insert succes");
        LogHandler.addLog("Insert " + table + " table, ID: " + id);
        return id;
    }

    private String getColumsNameForUpdate(Set<String> datas) {
        String setColumns = "";
        int counter = 1;
        for (String data : datas) {
            setColumns = counter != datas.size() ? setColumns + data + " = ?," : setColumns + data + " = ?";
            counter++;
        }
        return setColumns;
    }

    private String getColumsNameForInsert(Set<String> datas) {
        String setColumns = "";
        int counter = 1;
        for (String data : datas) {
            setColumns = counter != datas.size() ? setColumns + data + "," : setColumns + data;
            counter++;
        }
        return setColumns;
    }

    private String getPlaceHolders(int count) {
        String placeHolders = "";
        for (int i = 0; i < count; i++) {
            placeHolders = i - 1 == count ? "?" : "?,";
        }
        return placeHolders;
    }

    private void setMapValues(PreparedStatement preparedStatement, Collection<String> datas) throws SQLException {
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

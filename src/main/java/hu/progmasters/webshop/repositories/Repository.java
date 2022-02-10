package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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

    protected void update(String table, int id, Map<String, String> datas) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String update = "UPDATE " + table + " SET " + getColumsNameForUpdate(datas.keySet()) + " WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            setMapValues(preparedStatement, datas.values());
            preparedStatement.setInt(datas.size() + 1, id);
            System.out.println("Update succes");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update error!");
        }
    }

    protected void insert(String table, int id, Map<String, String> datas) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String update = "INSERT INTO " + table + "(" + getColumsNameForInsert(datas.keySet()) + ") VALUES(" + getPlaceHolders(datas.size()) + ");";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            setMapValues(preparedStatement, datas.values());
            System.out.println("Insert succes");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Insert error!");
        }
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
}

package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CustomerRepository extends Repository {

    private static final String TABLE = "customers";

    public CustomerRepository() {
        createTable();
    }

    public List<Customer> customerSearch(String keyword) {
        String sql = "SELECT * FROM customers " +
                "WHERE name LIKE ? " +
                "OR email LIKE ? " +
                "OR shipping_address LIKE ? " +
                "OR billing_address LIKE ?;";
        try (Connection connection = DatabaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, keyword);
            preparedStatement.setString(4, keyword);
            ResultSet result = preparedStatement.executeQuery();
            return getcustomersList(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Customer getCustomerByEmail(String email) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM customers WHERE email LIKE ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();
            return getcustomersList(result).get(0);
        } catch (SQLException e) {
            System.out.println("Not find customer!");
        }
        return null;
    }

    public Customer getCustomerById(int id) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM customers WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            return getcustomersList(result).get(0);
        } catch (SQLException e) {
            System.out.println("Not find customer!");
        }
        return null;
    }

    public List<Customer> getAllCustomer() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM customers;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return getcustomersList(result);

        } catch (SQLException e) {
            System.out.println("Can't open database!");
        }
        return Collections.emptyList();
    }

    private List<Customer> getcustomersList(ResultSet result) throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        while (result.next()) {
            customerList.add(new Customer(result.getInt("id")
                    , result.getString("name")
                    , result.getString("shipping_address")
                    , result.getString("billing_address")
                    , result.getInt("discount")
                    , result.getString("email")
                    , result.getString("comapny_name")
                    , result.getBoolean("company")
                    , result.getString("tax_number")));
        }
        return customerList;
    }

    public int addCustomer(Map<String, String> data) {
        return insert(TABLE, data);
    }

    public void updateCustomer(Customer customer) {
        update(TABLE, customer.getId(), customer.getData());
    }

    private void createTable() {
        String customers = "CREATE TABLE IF NOT EXISTS customers("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(30) NOT NULL,"
                + "shipping_address VARCHAR(100) NOT NULL,"
                + "billing_address VARCHAR(100) DEFAULT '',"
                + "email VARCHAR(20) NOT NULL,"
                + "discount INT UNSIGNED DEFAULT 0,"
                + "company BOOLEAN DEFAULT 0,"
                + "company_name VARCHAR(100) DEFAULT '',"
                + "tax_number VARCHAR(14));";
        execute(customers);
    }
}

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

    public Customer getCustomerByEmail(String email) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM customers WHERE email LIKE '?';";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return new Customer(result.getInt("id")
                        , result.getString("name")
                        , result.getString("shipping_address")
                        , result.getString("billing_address")
                        , result.getInt("discount")
                        , result.getString("email")
                        , result.getBoolean("regular_costumer")
                        , result.getBoolean("company")
                        , result.getString("tax_number"));
            }
        } catch (SQLException e) {
            System.out.println("Not find customer!");
        }
        return null;
    }

    public List<Customer> getAllCustomer() {
        List<Customer> customerList = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM customers;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                customerList.add(new Customer(result.getInt("id")
                        , result.getString("name")
                        , result.getString("shipping_address")
                        , result.getString("billing_address")
                        , result.getInt("discount")
                        , result.getString("email")
                        , result.getBoolean("regular_costumer")
                        , result.getBoolean("company")
                        , result.getString("tax_number")));
            }
            return customerList;
        } catch (SQLException e) {
            System.out.println("Can't open database!");
            return Collections.emptyList();
        }
    }

    public void addCustomer(Map<String, String> data) {
        insert(TABLE, data);
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
                + "regular_costumer BOOLEAN DEFAULT 0,"
                + "discount INT DEFAULT 0,"
                + "company BOOLEAN DEFAULT 0),"
                + "tax_number VARCHAR(14));";
        execute(customers);
    }
}

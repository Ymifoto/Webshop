package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Address;
import hu.progmasters.webshop.domain.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerRepository extends Repository {

    private final List<Customer> customerList = new ArrayList<>();
    private static final String TABLE = "customers";

    public CustomerRepository() {
        createTable();
    }

    private void createTable() {

        String customers = "CREATE TABLE IF NOT EXISTS customers("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(30) NOT NULL,"
                + "shipping_address VARCHAR(100) NOT NULL,"
                + "billing_address VARCHAR(100),"
                + "email VARCHAR(20) NOT NULL,"
                + "regular_costumer BOOLEAN NOT NULL DEFAULT 0,"
                + "discount INT NOT NULL DEFAULT 0,"
                + "company BOOLEAN NOT NULL DEFAULT 0,"
                + "tax_number VARCHAR(14));";

        String address = "CREATE TABLE IF NOT EXISTS address("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "shipping_address VARCHAR(100) NOT NULL,"
                + "billing_address VARCHAR(100) NOT NULL,"
                + "tax_number VARCHAR(14)),"
                + "customer_id INT NOT NULL,"
                + "FOREIGN KEY (customer_id) REFERENCES customers(id));";

        execute(customers);
        execute(address);
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
                        , new Address(result.getString("shipping_address"), result.getString("billing_address"), result.getString("tax_number"))
                        , result.getInt("discount")
                        , result.getString("email")
                        , result.getBoolean("regular_costumer")
                        , result.getBoolean("company"));
            }
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

            while (result.next()) {
                customerList.add(new Customer(result.getInt("id")
                        , result.getString("name")
                        , new Address(result.getString("shipping_address"), result.getString("billing_address"), result.getString("tax_number"))
                        , result.getInt("discount")
                        , result.getString("email")
                        , result.getBoolean("regular_costumer")
                        , result.getBoolean("company")));
            }
            return customerList;
        } catch (SQLException e) {
            System.out.println("Can't open database!");
            return Collections.emptyList();
        }
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }
}

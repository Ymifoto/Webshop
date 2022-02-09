package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Customer;

import java.sql.*;

public class CustomerRepository implements Repository {

    public CustomerRepository() {
        createTable();
    }

    private void createTable() {

        String sql = "CREATE TABLE IF NOT EXISTS customers("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(30) NOT NULL,"
                + "shipping_address VARCHAR(100) NOT NULL,"
                + "billing_address VARCHAR(100),"
                + "email VARCHAR(20) NOT NULL,"
                + "regular_costumer BOOLEAN NOT NULL DEFAULT 0,"
                + "discount INT NOT NULL DEFAULT 0,"
                + "company BOOLEAN NOT NULL DEFAULT 0);";

        execute(sql);
    }

    public Customer getCustomerByEmail(String email) {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM customers WHERE email LIKE '?';";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return new Customer(result.getString("name")
                        , result.getString("shipping_address")
                        , result.getString("billing_address")
                        , result.getInt("discount")
                        , result.getString("email")
                        , result.getBoolean("regular_costumer"));
            }
        } catch (SQLException e) {
            System.out.println("Not find customer!");
        }
        return null;
    }

    public void getAllCustomer() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM customers;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                System.out.println(
                        "ID: " + result.getInt("id") + " "
                                + "Name: " + result.getString("name") + " "
                                + "Email: " + result.getString("email") + " "
                                + "Shipping address: " + result.getString("shipping_address") + " "
                                + "Billing address: " + result.getString("billing_address") + " "
                                + "Discount: " + result.getInt("discount") + "% "
                                + "Regular costumer: " + result.getBoolean("regular_costumer")
                );
            }
        } catch (SQLException e) {
            System.out.println("Can't open database!");
        }
    }
}

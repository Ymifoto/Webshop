package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Customer;

import java.sql.*;
import java.util.*;

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
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, keyword);
            preparedStatement.setString(4, keyword);
            ResultSet result = preparedStatement.executeQuery();
            return getCustomers(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email LIKE ?;";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();

            List<Customer> customer = getCustomers(result);
            return customer.isEmpty() ? Optional.empty() : Optional.of(customer.get(0));
        } catch (SQLException e) {
            System.out.println("Not find customer!");
        }
        return Optional.empty();
    }

    public Optional<Customer> getCustomerById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?;";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            List<Customer> customer = getCustomers(result);
            return customer.isEmpty() ? Optional.empty() : Optional.of(customer.get(0));
        } catch (SQLException e) {
            System.out.println("Not find customer!");
        }
        return Optional.empty();
    }

    public List<Customer> getAllCustomer() {
        String sql = "SELECT * FROM customers;";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            return getCustomers(result);

        } catch (SQLException e) {
            System.out.println("Can't open database!");
        }
        return Collections.emptyList();
    }

    public int addCustomer(Map<String, String> data) {
        return insert(TABLE, data);
    }

    public void updateCustomer(Customer customer) {
        update(TABLE, customer.getId(), customer.getData());
    }

    private List<Customer> getCustomers(ResultSet result) throws SQLException {
        List<Customer> customerList = new ArrayList<>();
        while (result.next()) {
            customerList.add(new Customer(result.getInt("id")
                    , result.getString("name")
                    , result.getString("shipping_address")
                    , result.getString("billing_address")
                    , result.getString("email")
                    , result.getString("company_name")
                    , result.getBoolean("company")
                    , result.getString("tax_number")));
        }
        return customerList;
    }

    private void createTable() {
        String customers = "CREATE TABLE IF NOT EXISTS customers("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(30) NOT NULL,"
                + "shipping_address VARCHAR(100) NOT NULL,"
                + "billing_address VARCHAR(100) DEFAULT '',"
                + "email VARCHAR(20) NOT NULL,"
                + "company BOOLEAN DEFAULT 0,"
                + "company_name VARCHAR(100) DEFAULT '',"
                + "tax_number VARCHAR(14));";
        execute(customers);
    }
}

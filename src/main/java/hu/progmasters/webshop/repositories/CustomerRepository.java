package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Customer;

import java.sql.*;
import java.util.*;

public class CustomerRepository extends Repository {

    private static final String TABLE = "customers";
    private static final String ADDRESS_TABLE = "address";

    public Set<Customer> customerSearch(String keyword) {
        String sql = "SELECT * FROM customers AS c " +
                "JOIN address AS a ON a.customer_id = c.id " +
                "WHERE name LIKE ? " +
                "OR email LIKE ? " +
                "OR company_name LIKE ?" +
                "OR city LIKE ? " +
                "OR street LIKE ? " +
                "OR zip = ? " +
                "AND billing_address = 0;";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, keyword);
            preparedStatement.setString(4, keyword);
            preparedStatement.setString(5, keyword);
            preparedStatement.setString(6, keyword);
            ResultSet result = preparedStatement.executeQuery();
            return getCustomers(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        String sql = "SELECT * FROM customers AS c WHERE email LIKE ?;";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();

            Set<Customer> customer = getCustomers(result);
            return customer.isEmpty() ? Optional.empty() : customer.stream().findFirst();
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

            Set<Customer> customer = getCustomers(result);
            return customer.isEmpty() ? Optional.empty() : customer.stream().findFirst();
        } catch (SQLException e) {
            System.out.println("Not find customer!");
        }
        return Optional.empty();
    }

    public Set<Customer> getAllCustomer() {
        String sql = "SELECT * FROM customers;";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            return getCustomers(result);

        } catch (SQLException e) {
            System.out.println("Can't open database!");
        }
        return Collections.emptySet();
    }

    public int addCustomer(Customer customer) {
        int customerId = insert(TABLE, customer.getData());
        addAddresses(customer.getShippingAddress().getData(), customerId);
        addAddresses(customer.getBillingAddress().getData(), customerId);
        return customerId;
    }

    public void addAddresses(Map<String, String> data, int customerId) {
        data.put("customer_id", String.valueOf(customerId));
        insert(ADDRESS_TABLE, data);
    }

    public void updateCustomer(Customer customer) {
        update(TABLE, customer.getId(), customer.getData());
        updateAddress(customer);
    }

    private void updateAddress(Customer customer) {
        update(ADDRESS_TABLE, customer.getShippingAddress().getId(), customer.getShippingAddress().getData());
        update(ADDRESS_TABLE, customer.getBillingAddress().getId(), customer.getBillingAddress().getData());

    }

    private Set<Customer> getCustomers(ResultSet result) throws SQLException {
        Set<Customer> customerList = new TreeSet<>();
        while (result.next()) {
            customerList.add(new Customer(result.getInt("id")
                    , result.getString("name")
                    , getAddress(result.getInt("id"), false)
                    , getAddress(result.getInt("id"), true)
                    , result.getString("email")
                    , result.getString("company_name")
                    , result.getBoolean("company")
                    , result.getString("tax_number")));
        }
        return customerList;
    }
}

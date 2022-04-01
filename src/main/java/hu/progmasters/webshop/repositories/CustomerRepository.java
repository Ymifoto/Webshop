package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.domain.Customer;

import java.sql.*;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class CustomerRepository extends Repository {

    private static final CustomerRepository CUSTOMER_REPOSITORY = new CustomerRepository();
    private static final String TABLE = "customers";
    private final AddressRepository addressRepository = AddressRepository.getRepository();

    private CustomerRepository() {
    }

    public static CustomerRepository getRepository() {
        return CUSTOMER_REPOSITORY;
    }

    public Set<Customer> customerSearch(String keyword) {
        keyword = "%" + keyword + "%";
        String sql = "SELECT * FROM customers AS c " +
                "JOIN address AS a ON a.customer_id = c.id " +
                "WHERE name LIKE ? " +
                "OR email LIKE ? " +
                "OR company_name LIKE ? " +
                "OR city LIKE ? " +
                "OR street LIKE ? ;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, keyword);
            preparedStatement.setString(4, keyword);
            preparedStatement.setString(5, keyword);
            ResultSet result = preparedStatement.executeQuery();
            return makeCustomers(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptySet();
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        String sql = "SELECT * FROM customers AS c WHERE email LIKE ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();

            Set<Customer> customer = makeCustomers(result);
            return customer.isEmpty() ? Optional.empty() : customer.stream().findFirst();
        } catch (SQLException e) {
            System.out.println("Not find customer!");
        }
        return Optional.empty();
    }

    public Optional<Customer> getCustomerById(int id) {
        String sql = "SELECT * FROM customers WHERE id = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            Set<Customer> customer = makeCustomers(result);
            return customer.isEmpty() ? Optional.empty() : customer.stream().findFirst();
        } catch (SQLException e) {
            System.out.println("Not find customer!");
        }
        return Optional.empty();
    }

    public Set<Customer> getAllCustomer() {
        String sql = "SELECT * FROM customers;";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            return makeCustomers(result);

        } catch (SQLException e) {
            System.out.println("Can't open database!");
        }
        return Collections.emptySet();
    }

    public int addCustomer(Customer customer) {
        int customerId = insert(TABLE, customer.getData());
        addressRepository.addAddresses(customer.getShippingAddress().getData(), customerId);
        if (!customer.isSameAddress()) {
            addressRepository.addAddresses(customer.getBillingAddress().getData(), customerId);
        }
        return customerId;
    }

    public void updateCustomer(Customer customer) {
        update(TABLE, customer.getId(), customer.getData());
        addressRepository.updateAddress(customer);
    }

    private Set<Customer> makeCustomers(ResultSet result) throws SQLException {
        Set<Customer> customerList = new TreeSet<>();
        while (result.next()) {
            Customer customer = new Customer(result.getInt("id")
                    , result.getString("name")
                    , addressRepository.getAddress(result.getInt("id"), false)
                    , result.getString("email")
                    , result.getString("company_name")
                    , result.getBoolean("company")
                    , result.getString("tax_number")
                    , result.getBoolean("same_address"));

            if (!customer.isSameAddress()) {
                customer.setBillingAddress(addressRepository.getAddress(customer.getId(), true));
            } else {
                customer.setBillingAddress(customer.getShippingAddress());
            }
            customerList.add(customer);
        }
        return customerList;
    }
}
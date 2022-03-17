package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Address;
import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.handlers.OutputHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class AddressRepository {

    private static final String TABLE = "address";
    private final Repository repository;

    public AddressRepository(Repository repository) {
        this.repository = repository;
    }

    public void addAddresses(Map<String, String> data, int customerId) {
        data.put("customer_id", String.valueOf(customerId));
        repository.insert(TABLE, data);
    }

    public void updateAddress(Customer customer) {
        repository.update(TABLE, customer.getShippingAddress().getId(), customer.getShippingAddress().getData());
        if (!customer.isSameAddress()) {
            updateBillingAddress(customer.getBillingAddress());
        }
    }

    public void updateBillingAddress(Address address) {
        if (address.getId() == 0) {
            address.setId(repository.insert(TABLE, address.getData()));
        } else {
            repository.update(TABLE, address.getId(), address.getData());
        }
    }

    public Address getAddress(int id, boolean billing) {
        Address address = new Address();
        String sql = "SELECT * FROM address WHERE customer_id = ? AND billing_address = ?;";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setBoolean(2, billing);
            ResultSet result = preparedStatement.executeQuery();

            address = createAddress(result);

        } catch (SQLException e) {
            OutputHandler.outputRed("Cant read database");
            e.printStackTrace();
        }
        return address;
    }

    private Address createAddress(ResultSet result) throws SQLException {
        Address address = new Address();
        if (result.next()) {
            address.setId(result.getInt("id"))
                    .setCustomerId(result.getInt("customer_id"))
                    .setZip(result.getInt("zip"))
                    .setCity(result.getString("city"))
                    .setStreet(result.getString("street"))
                    .setBillingAddress(result.getBoolean("billing_address"));
        }
        return address;
    }
}

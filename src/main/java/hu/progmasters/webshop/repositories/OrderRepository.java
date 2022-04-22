package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.Order;
import hu.progmasters.webshop.domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends Repository {

    private final AddressRepository addressRepository = new AddressRepository();

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders AS o " +
                "JOIN customers AS c ON c.id = o.customer_id " +
                "JOIN shipping_methods AS sm ON o.shipping_method = sm.id " +
                "JOIN payment_methods AS pm ON o.payment_method = pm.id;";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            getOrdersList(result, orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getInProgressOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders AS o " +
                "JOIN customers AS c ON c.id = o.customer_id " +
                "JOIN shipping_methods AS sm ON o.shipping_method = sm.id " +
                "JOIN payment_methods AS pm ON o.payment_method = pm.id " +
                "WHERE o.shipped = 0;";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            getOrdersList(result, orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void setOrderShipped(int id) {
        String sql = "UPDATE orders SET shipped = 1 WHERE id = " + id;
        execute(sql);
    }

    public List<Order> orderSearch(String keyword) {
        List<Order> ordersList = new ArrayList<>();
        String sql = "SELECT * FROM orders AS o "
                + "JOIN customers AS c ON o.customer_id = c.id "
                + "JOIN shipping_methods AS sm ON o.shipping_method = sm.id "
                + "JOIN payment_methods AS pm ON o.payment_method = pm.id "
                + "JOIN address AS a ON c.id = a.customer_id "
                + "WHERE email LIKE ? "
                + "OR name LIKE ? "
                + "OR o.id LIKE ? "
                + "OR company_name LIKE ? ;";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, keyword);
            preparedStatement.setString(4, keyword);
            ResultSet result = preparedStatement.executeQuery();
            getOrdersList(result, ordersList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    private void getOrdersList(ResultSet result, List<Order> ordersList) throws SQLException {
        while (result.next()) {
            Customer customer = new Customer(result.getInt("customer_id")
                    , result.getString("name")
                    , addressRepository.getAddress(result.getInt("customer_id"), false)
                    , result.getString("email")
                    , result.getString("company_name")
                    , Boolean.parseBoolean(result.getString("company"))
                    , result.getString("tax_number")
                    , result.getBoolean("same_address"));

            if (!customer.isSameAddress()) {
                customer.setBillingAddress(addressRepository.getAddress(result.getInt("customer_id"), true));
            }

            Order order = new Order(result.getInt(1)
                    , customer
                    , result.getString("sm_name")
                    , result.getString("pm_name")
                    , result.getInt("shipping_cost")
                    , result.getInt("order_total")
                    , result.getDate("order_time")
                    , result.getBoolean("shipped")
            );
            order.getOrderedProducts().addAll(getOrderedProducts(order.getId()));
            ordersList.add(order);
        }
    }

    private List<Product> getOrderedProducts(int orderId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products AS p " +
                "JOIN ordered_products AS op ON p.id = op.product_id " +
                "WHERE op.order_id = " + orderId;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            while (result.next()) {
                products.add(createProduct(result));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}

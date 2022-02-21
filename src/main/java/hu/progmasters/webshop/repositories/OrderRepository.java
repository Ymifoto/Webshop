package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.Order;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends Repository {

    public OrderRepository() {
        createTable();
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM orders AS o " +
                    "JOIN customers AS c ON c.id = o.customer_id " +
                    "JOIN shipping_methods AS sm ON o.shipping_method = sm.id "+
                    "JOIN payment_methods AS pm ON o.payment_method = pm.id;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            getOrdersList(result,orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> getInProgressOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM orders AS o " +
                    "JOIN customers AS c ON c.id = o.customer_id " +
                    "JOIN shipping_methods AS sm ON o.shipping_method = sm.id "+
                    "JOIN payment_methods AS pm ON o.payment_method = pm.id " +
                    "WHERE o.shipped = 0;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            getOrdersList(result,orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void setOrderShippedDone(int id) {
            String sql = "UPDATE orders SET shipped = 1 WHERE id = " + id;
            execute(sql);
    }

    public List<Order> orderSearch(String keyword) {
        List<Order> ordersList = new ArrayList<>();
        String sql = "SELECT * FROM orders AS o "
                + "JOIN customers AS c ON o.customer_id = c.id "
                + "JOIN shipping_methods AS sm ON o.shipping_method = sm.id "
                + "JOIN payment_methods AS pm ON o.payment_method = pm.id "
                + "WHERE customer_id LIKE ? "
                + "OR billing_address LIKE ? "
                + "OR email LIKE ? "
                + "OR name LIKE ? "
                + "OR company_name LIKE ? "
                + "OR shipping_address LIKE ?";

        try (Connection connection = DatabaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, keyword);
            preparedStatement.setString(3, keyword);
            preparedStatement.setString(4, keyword);
            preparedStatement.setString(5, keyword);
            preparedStatement.setString(6, keyword);
            ResultSet result = preparedStatement.executeQuery();
            getOrdersList(result, ordersList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    private void getOrdersList(ResultSet result, List<Order> ordersList) throws SQLException {
        while (result.next()) {
            Order order = new Order(result.getInt(1)
                    ,new Customer(result.getInt("customer_id")
                                    ,result.getString("name")
                                    ,result.getString("shipping_address")
                                    ,result.getString("billing_address")
                                    ,result.getString("email")
                                    ,result.getString("company_name")
                                    ,Boolean.parseBoolean(result.getString("company"))
                                    ,result.getString("tax_number"))
                    ,result.getString("sm_name")
                    ,result.getString("pm_name")
                    ,result.getInt("shipping_cost")
                    ,result.getInt("order_total")
                    ,result.getString("order_time")
                    ,result.getBoolean("shipped")
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
        try (Connection connection = DatabaseConfig.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                products.add(new Product(result.getInt("product_id")
                        , result.getString("name")
                        , result.getString("vendor")
                        , result.getInt("price")
                        , result.getInt("sale_price")
                        , result.getString("description")
                        , result.getString("product_type")
                        , Tax.valueOf(result.getString("tax"))
                        , result.getBoolean("in_stock")));
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    private void createTable() {

        String ordersTable = "CREATE TABLE IF NOT EXISTS orders("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "customer_id INT NOT NULL,"
                + "payment_method INT NOT NULL,"
                + "shipping_method INT NOT NULL,"
                + "order_total INT UNSIGNED NOT NULL,"
                + "shipping_cost INT UNSIGNED NOT NULL,"
                + "shipped BOOLEAN DEFAULT 0,"
                + "order_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (customer_id) REFERENCES customers(id),"
                + "FOREIGN KEY (payment_method) REFERENCES payment_methods(id),"
                + "FOREIGN KEY (shipping_method) REFERENCES shipping_methods(id));";

        String orderedProductsTable = "CREATE TABLE IF NOT EXISTS ordered_products("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "product_id INT NOT NULL,"
                + "order_id INT NOT NULL,"
                + "FOREIGN KEY (product_id) REFERENCES products(id),"
                + "FOREIGN KEY (order_id) REFERENCES orders(id));";


        execute(ordersTable);
        execute(orderedProductsTable);
    }
}

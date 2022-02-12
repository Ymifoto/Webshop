package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.Order;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends Repository {

    private static final String TABLE = "orders";


    public OrderRepository() {
        createTable();
    }

    public void getShippingMethods() {
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM shipping_methods";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println(result.getInt("id") + ". " + result.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("No shipping methods");
            e.printStackTrace();
        }
    }

    public void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM orders AS o" +
                    "JOIN customers AS c ON c.id = o.customer_id " +
                    "JOIN shipping_methods AS sm ON o.shipping_method = sm.id " +
                    "JOIN payment_methods AS pm ON o.payment_method = pm.id;";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                int orderId = result.getInt(1);
                Order order  = new Order(orderId
                        , new Customer(result.getInt("customer_id")
                        , result.getString("name")
                        , result.getString("shipping_address")
                        , result.getString("billing_address")
                        , result.getInt("discount"), result.getString("email")
                        , result.getBoolean("regulas_customer")
                        , result.getBoolean("company")
                        , result.getString("tax_number"))
                        , result.getString("sm_name")
                        , result.getInt("shipping_cost")
                        , result.getInt("order_total")
                        , result.getString("order_time"));
                order.getOrderedProducts().addAll(getOrderedProducts(orderId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Product> getOrderedProducts(int orderId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM ordered_products AS op " +
                "JOIN products AS p ON p.id = op.id " +
                "WHERE op.id = " + orderId;
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

        String paymentMethodsTable = "CREATE TABLE IF NOT EXISTS payment_methods("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "pm_name VARCHAR(10) NOT NULL UNIQUE);";

        String shippingMethodsTable = "CREATE TABLE IF NOT EXISTS shipping_methods("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "sm_name VARCHAR(20) NOT NULL UNIQUE);";

        String shippingPriceTable = "CREATE TABLE IF NOT EXISTS shipping_price("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "price INT UNSIGNED NOT NULL,"
                + "amount_min INT UNSIGNED NOT NULL,"
                + "amount_max INT UNSIGNED NOT NULL,"
                + "shipping_method INT UNSIGNED NOT NULL);";

        String ordersTable = "CREATE TABLE IF NOT EXISTS orders("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "customer_id INT NOT NULL,"
                + "payment_method INT NOT NULL,"
                + "shipping_method INT NOT NULL,"
                + "order_total INT UNSIGNED NOT NULL,"
                + "shipping_cost INT UNSIGNED NOT NULL,"
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

        execute(paymentMethodsTable);
        execute(shippingMethodsTable);
        execute(shippingPriceTable);
        execute(ordersTable);
        execute(orderedProductsTable);
    }
}

package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.handlers.OutputHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CheckoutRepository extends Repository {

    private static final String TABLE = "orders";

    public int saveOrder(Map<String, String> order, List<Integer> orderedProductsId) {
        int id = insert(TABLE, order);
        updateOrderedProductsTable(id, orderedProductsId);
        return id;
    }

    public Map<Integer, Integer> getShippingMethods(int orderTotal) {
        Map<Integer, Integer> shippingMethods = new TreeMap<>();
        String sql = "SELECT sm.id, sm_name, sp.price FROM shipping_methods AS sm " +
                "JOIN shipping_price AS sp ON sp.shipping_method = sm.id " +
                "WHERE sp.amount_min < " + orderTotal + " AND sp.amount_max >= " + orderTotal + ";";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            while (result.next()) {
                shippingMethods.put(result.getInt("id"), result.getInt("price"));
                OutputHandler.outputYellow(result.getInt("id") + ". " + result.getString("sm_name") + " " + result.getInt("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingMethods;
    }

    public Map<Integer, String> getPaymentMethods() {
        Map<Integer, String> paymentMethods = new TreeMap<>();
        String sql = "SELECT * FROM payment_methods";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            while (result.next()) {
                paymentMethods.put(result.getInt("id"), result.getString("pm_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentMethods;
    }

    public String getShippingMethodName(int methodId) {
        String sql = "SELECT sm_name FROM shipping_methods WHERE id = " + methodId;
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            if (result.next()) {
                return result.getString("sm_name");
            }
        } catch (SQLException e) {
            System.out.println("No shipping methods!");
        }
        return null;
    }

    public void updateOrderedProductsTable(int orderId, List<Integer> productsId) {
        Map<String, String> datas = new TreeMap<>();
        datas.put("order_id", String.valueOf(orderId));
        for (Integer productId : productsId) {
            datas.put("product_id", productId.toString());
            insert("ordered_products", datas);
        }
    }
}

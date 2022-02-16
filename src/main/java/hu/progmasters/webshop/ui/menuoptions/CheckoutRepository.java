package hu.progmasters.webshop.ui.menuoptions;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.repositories.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CheckoutRepository extends Repository {

    private static final String TABLE = "orders";


    public void saveOrder(Map<String, String> order, List<Integer> orderedProductsId) {
        updateOrderedProductsTable(insert(TABLE, order), orderedProductsId);
    }

    public Map<Integer, Integer> getShippingMethods(int orderTotal) {
        Map<Integer, Integer> shippingMethods = new TreeMap<>();
        String sql = "SELECT sm.id, sm_name, sp.price FROM shipping_methods AS sm " +
                "JOIN shipping_price AS sp ON sp.shipping_method = sm.id " +
                "WHERE sp.amount_min < " + orderTotal + " AND sp.amount_max >= " + orderTotal + ";";
        try (Connection connection = DatabaseConfig.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                shippingMethods.put(result.getInt("id"), result.getInt("price"));
                OutputHandler.outputYellow(result.getInt("id") + ". " + result.getString("sm_name") + " " + result.getInt("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingMethods;
    }

    public String getShippingMethodName(int methodId) {
        String sql = "SELECT sm_name FROM shipping_methods WHERE id = " + methodId;
        try (Connection connection = DatabaseConfig.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                return result.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("No shipiing methods!");
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

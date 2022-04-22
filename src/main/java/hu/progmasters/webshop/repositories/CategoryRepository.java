package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.handlers.OutputHandler;

import java.sql.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CategoryRepository extends Repository {

    private static final String TABLE = "categories_name";

    public Category getCategroyById(int id) {
        String sql = "SELECT * FROM categories_name WHERE id = ?;";
        Category category = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                category = new Category();
                category.setId(result.getInt("id"));
                category.setName(result.getString("category_name"));
                category.setDescription(result.getString("category_desc"));
                setCategoryProducts(category);
            }

        } catch (SQLException e) {
            OutputHandler.outputRed("Database error!");
        }
        return category;
    }

    public Map<String, String> getCategoryList() {
        Map<String, String> data = new TreeMap<>(Comparator.comparingInt(Integer::parseInt));

        String sql = "SELECT cn.id,category_name, COUNT(c.product_id) AS product_number " +
                "FROM categories_name AS cn " +
                "LEFT JOIN categories AS c ON c.category_id = cn.id " +
                "GROUP BY cn.id " +
                "ORDER BY cn.id;";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            while (result.next()) {
                data.put(String.valueOf(result.getInt("id")), result.getString("category_name") + " (" + result.getInt("product_number") + " products)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public int addCategory(Map<String, Object> data) {
        int id = insert(TABLE, data);
        updateCategoriesTable();
        return id;
    }

    public void updateCategory(Category category) {
        update(TABLE, category.getId(), category.getData());
    }

    private void setCategoryProducts(Category category) throws SQLException {
        String sql = "SELECT name,vendor_name,price,sale_price,description,product_type_name,tax,on_sale,in_stock,product_id " +
                "FROM products AS p " +
                "RIGHT JOIN categories AS c ON p.id = c.product_id " +
                "RIGHT JOIN categories_name AS cn ON c.category_id = cn.id " +
                "RIGHT JOIN product_types AS pt ON p.product_type = pt.id " +
                "RIGHT JOIN vendors AS v ON p.vendor = v.id " +
                "WHERE cn.id = ?;";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, category.getId());
            ResultSet result = preparedStatement.executeQuery();
            List<Product> productList = category.getProducts();
            while (result.next()) {
                if (result.getInt("product_id") != 0) {
                    productList.add(createProduct(result));
                }
            }
        } catch (SQLException e) {
            OutputHandler.outputRed("Database error!");
        }
    }
}

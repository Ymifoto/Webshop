package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;
import hu.progmasters.webshop.handlers.OutputHandler;

import java.sql.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CategoryRepository extends Repository {

    private static final String TABLE = "categories_name";

    public Category getCategroyById(int id) {
        String sql = "SELECT name,vendor_name,price,sale_price,description,product_type_name,tax,on_sale,in_stock,product_id,category_name " +
                "FROM products AS p " +
                "RIGHT JOIN categories AS c ON p.id = c.product_id " +
                "RIGHT JOIN categories_name AS cn ON c.category_id = cn.id " +
                "RIGHT JOIN product_types AS pt ON p.product_type = pt.id " +
                "RIGHT JOIN vendors AS v ON p.vendor = v.id " +
                "WHERE cn.id = ?;";

        Category category = new Category();
        category.setId(id);

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            getProductList(result, category);

        } catch (SQLException e) {
            System.out.println("Not found category");
        }
        return category;
    }

    public void getCategoryList() {
        Map<String, String> data = new TreeMap<>(Comparator.comparingInt(Integer::parseInt));

        String sql = "SELECT cn.id,category_name, COUNT(c.product_id) AS product_number " +
                "FROM categories_name AS cn " +
                "LEFT JOIN categories AS c ON c.category_id = cn.id " +
                "GROUP BY cn.id " +
                "ORDER BY cn.id;";
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)
        ) {
            while (result.next()) {
                data.put(String.valueOf(result.getInt("id")), result.getString("category_name") + " (" + result.getInt("product_number") + " products)");
            }
            OutputHandler.printMapStringKey(data, "ID", "Categories");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCategory(Map<String, String> data) {
        insert(TABLE, data);
        updateCategoriesTable();
    }

    public void updateCategory(Category category) {
        update(TABLE, category.getId(), category.getData());
    }

    private void getProductList(ResultSet result, Category category) throws SQLException {
        List<Product> productList = category.getProducts();
        while (result.next()) {
            if (category.getName() == null) {
                category.setName(result.getString("category_name"));
            }
            if (result.getInt("product_id") != 0) {
                productList.add(new Product(
                        result.getInt("product_id"),
                        result.getString("name"),
                        result.getString("vendor_name"),
                        result.getInt("price"),
                        result.getInt("sale_price"),
                        result.getString("description"),
                        result.getString("product_type_name"),
                        Tax.valueOf(result.getString("tax")),
                        result.getBoolean("in_stock")));
            }
        }
    }
}

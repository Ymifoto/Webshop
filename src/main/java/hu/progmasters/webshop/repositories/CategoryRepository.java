package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository extends Repository {

    private static final String TABLE = "categories_name";

    public CategoryRepository() {
        createTable();
    }


    public Category getCategroyById(int id) {
        Category category = new Category();
        try (Connection connection = DatabaseConfig.getConnection()) {
            String sql = "SELECT * FROM products AS p" +
                    "JOIN categories AS c ON p.id = c.product_id" +
                    "JOIN categories_name AS cn ON c.category_id = cn.id" +
                    "WHERE cn.id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            category.setProducts(getProductList(result));

        } catch (SQLException e) {
            System.out.println("Not found category");
            return null;
        }
        category.setId(id);
        return category;
    }

    private List<Product> getProductList(ResultSet result) throws SQLException {
        List<Product> products = new ArrayList<>();

        while (result.next()) {
            products.add(new Product(result.getInt("product_id"), result.getString("name"),
                    result.getString("vendor"), result.getInt("price"),
                    result.getInt("sale_price"), result.getString("description"),
                    result.getString("product_type"), Tax.valueOf(result.getString("tax"))
                    , result.getBoolean("on_sale"), result.getBoolean("in_stock")));
        }
        return products;
    }

    private void createTable() {

        String categoriesNameTable = "CREATE TABLE IF NOT EXISTS categories_name("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "category_name VARCHAR(30) NOT NULL UNIQUE,"
                + "description VARCHAR(255));";

        String categoriesTable = "CREATE TABLE IF NOT EXISTS categories("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "product_id INT NOT NULL,"
                + "category_id INT NOT NULL,"
                + "FOREIGN KEY (product_id) REFERENCES products(id),"
                + "FOREIGN KEY (category_id) REFERENCES categories_name(id));";

        execute(categoriesNameTable);
        execute(categoriesTable);
    }
}

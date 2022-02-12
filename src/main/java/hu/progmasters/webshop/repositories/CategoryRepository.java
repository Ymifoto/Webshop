package hu.progmasters.webshop.repositories;

import hu.progmasters.webshop.DatabaseConfig;
import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.Tax;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class CategoryRepository extends Repository {

    private static final String TABLE = "categories_name";
    private final WebShopRepository webShopRepository;

    public CategoryRepository(WebShopRepository webShopRepository) {
        createTable();
        this.webShopRepository = webShopRepository;
    }

    public Category getCategroyById(int id) {
        String sql = "SELECT name,vendor,price,sale_price,description,product_type,tax,on_sale,in_stock,product_id,category_name " +
                "FROM products AS p " +
                "JOIN categories AS c ON p.id = c.product_id " +
                "JOIN categories_name AS cn ON c.category_id = cn.id " +
                "WHERE cn.id = ?;";

        Category category = new Category();
        category.setId(id);

        try (Connection connection = DatabaseConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            getProductList(result, category);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Not found category");
        }
        return category;
    }

    public void getCategoryList() {
        String sql = "SELECT cn.id,category_name, COUNT(c.product_id) AS product_number " +
                "FROM categories_name AS cn " +
                "LEFT JOIN categories AS c ON c.category_id = cn.id " +
                "GROUP BY cn.id " +
                "ORDER BY cn.id;";
        try (Connection connection = DatabaseConfig.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            printOutCategories(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCategory(Map<String, String> data) {
        insert(TABLE, data);
        webShopRepository.updateCategoriesTable();
    }

    public void updateCategory(int id, Map<String, String> data) {
        update(TABLE, id, data);
    }

    private void getProductList(ResultSet result, Category category) throws SQLException {
        List<Product> productList = category.getProducts();
        while (result.next()) {
            if (category.getName() == null) {
                category.setName(result.getString("category_name"));
            }

            productList.add(new Product(result.getInt("product_id"), result.getString("name"),
                    result.getString("vendor"), result.getInt("price"),
                    result.getInt("sale_price"), result.getString("description"),
                    result.getString("product_type"), Tax.valueOf(result.getString("tax")),
                    result.getBoolean("on_sale"), result.getBoolean("in_stock")));
        }
    }

    private void createTable() {

        String categoriesNameTable = "CREATE TABLE IF NOT EXISTS categories_name("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "category_name VARCHAR(50) NOT NULL UNIQUE,"
                + "category_desc VARCHAR(100));";

        String categoriesTable = "CREATE TABLE IF NOT EXISTS categories("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "product_id INT NOT NULL UNIQUE,"
                + "category_id INT NOT NULL,"
                + "FOREIGN KEY (product_id) REFERENCES products(id),"
                + "FOREIGN KEY (category_id) REFERENCES categories_name(id));";

        execute(categoriesNameTable);
        execute(categoriesTable);
    }

    private void printOutCategories(ResultSet result) throws SQLException {
        String line = "";
        int count = 0;
        while (result.next()) {
            count++;
            line = line + "ID: " + result.getInt("id") + ", "
                    + result.getString("category_name") + ", "
                    + result.getInt("product_number") + " products <|> ";
            if (count % 3 == 0) {
                System.out.println(line);
                line = "";
            }
        }
    }
}

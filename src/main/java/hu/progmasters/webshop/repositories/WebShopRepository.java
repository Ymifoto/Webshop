package hu.progmasters.webshop.repositories;

public class WebShopRepository extends Repository {


    public void updateCategoriesTable() {
        String sql = "INSERT IGNORE INTO categories(product_id,category_id) SELECT id,category_id FROM products WHERE category_id IS NOT NULL;";
        execute(sql);
    }
}

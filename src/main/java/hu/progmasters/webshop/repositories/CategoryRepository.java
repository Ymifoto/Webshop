package hu.progmasters.webshop.repositories;

public class CategoryRepository extends Repository {

    private static final String TABLE = "categories_name";

    public CategoryRepository() {
        createTable();
    }

    private void createTable(){

        String categoriesNameTable = "CREATE TABLE IF NOT EXISTS categories_name("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name VARCHAR(30) NOT NULL UNIQUE,"
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

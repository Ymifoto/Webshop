package hu.progmasters.webshop.repositories;

public enum Tables {

    VENDORS("CREATE TABLE IF NOT EXISTS vendors("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "vendor_name VARCHAR(30) NOT NULL UNIQUE);"),

    PRODUCT_TYPES("CREATE TABLE IF NOT EXISTS product_types("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "product_type_name VARCHAR(40) NOT NULL UNIQUE);"),

    PRODUCTS("CREATE TABLE IF NOT EXISTS products("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "name VARCHAR(100) NOT NULL,"
            + "vendor INT NOT NULL,"
            + "price INT UNSIGNED NOT NULL,"
            + "sale_price INT UNSIGNED DEFAULT 0,"
            + "description VARCHAR(255),"
            + "product_type INT NOT NULL,"
            + "tax VARCHAR(10) NOT NULL,"
            + "category_id INT UNSIGNED,"
            + "on_sale BOOLEAN NOT NULL DEFAULT 0,"
            + "in_stock BOOLEAN NOT NULL DEFAULT 1, "
            + "FOREIGN KEY (product_type) REFERENCES product_types(id), "
            + "FOREIGN KEY (vendor) REFERENCES vendors(id));"),

    CUSTOMERS("CREATE TABLE IF NOT EXISTS customers("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "name VARCHAR(30) NOT NULL,"
            + "email VARCHAR(20) NOT NULL,"
            + "company BOOLEAN DEFAULT 0,"
            + "company_name VARCHAR(100),"
            + "tax_number VARCHAR(14));"),

    CATEGORIES_NAME("CREATE TABLE IF NOT EXISTS categories_name("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "category_name VARCHAR(50) NOT NULL UNIQUE,"
            + "category_desc VARCHAR(100));"),

    CATEGORIES("CREATE TABLE IF NOT EXISTS categories("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "product_id INT NOT NULL UNIQUE,"
            + "category_id INT NOT NULL,"
            + "FOREIGN KEY (product_id) REFERENCES products(id),"
            + "FOREIGN KEY (category_id) REFERENCES categories_name(id));"),

    PAYMENT_METHODS("CREATE TABLE IF NOT EXISTS payment_methods("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "pm_name VARCHAR(30) NOT NULL UNIQUE);"),

    SHIPPING_METHODS("CREATE TABLE IF NOT EXISTS shipping_methods("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "sm_name VARCHAR(30) NOT NULL UNIQUE);"),

    SHIPPING_PRICE("CREATE TABLE IF NOT EXISTS shipping_price( "
            + "id INT PRIMARY KEY AUTO_INCREMENT, "
            + "price INT UNSIGNED NOT NULL, "
            + "amount_min INT UNSIGNED NOT NULL, "
            + "amount_max INT UNSIGNED NOT NULL, "
            + "shipping_method INT NOT NULL, "
            + "FOREIGN KEY (shipping_method) REFERENCES shipping_methods(id));"),

    ORDERS("CREATE TABLE IF NOT EXISTS orders("
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
            + "FOREIGN KEY (shipping_method) REFERENCES shipping_methods(id));"),

    ORDERED_PRODUCTS("CREATE TABLE IF NOT EXISTS ordered_products("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "product_id INT NOT NULL,"
            + "order_id INT NOT NULL,"
            + "FOREIGN KEY (product_id) REFERENCES products(id), "
            + "FOREIGN KEY (order_id) REFERENCES orders(id));"),

    ADDRESS( "CREATE TABLE IF NOT EXISTS address("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "customer_id INT NOT NULL,"
            + "zip INT UNSIGNED NOT NULL,"
            + "city VARCHAR(30) NOT NULL,"
            + "street VARCHAR(100) NOT NULL,"
            + "billing_address BOOLEAN DEFAULT 0, "
            + "FOREIGN KEY (customer_id) REFERENCES customers(id));");


    private final String sql;

    Tables(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}

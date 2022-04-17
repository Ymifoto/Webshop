package repositories;

import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.repositories.AdminRepository;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.repositories.Repository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    private static final AdminRepository adminRepository = new AdminRepository();
    private static final ProductRepository productRepository = new ProductRepository();
    private List<Product> productList;


    @BeforeAll
    static void initDataBase() {
        Repository.setTestMode(true);
        if (!Repository.isTestDatabaseCreated()) {
            adminRepository.createTables();
            adminRepository.loadTestData();
            Repository.setTestDatabaseCreated(true);
        }
    }

    @Test
    void getProductTypesTest() {
        assertFalse(productRepository.getProductTypes().isEmpty());
    }

    @Test
    void productSearchValidTest() {
        productList = productRepository.productSearch("JBL");
        assertThat(productList)
                .isNotEmpty()
                .extracting(Product::getVendor)
                .allMatch(v -> v.equals("JBL"));
    }

    @Test
    void productSearchNotValidTest() {
        assertTrue(productRepository.productSearch("xxxxxx").isEmpty());
    }

    @Test
    void getProductByIdTest_ProductId_Valid() {
        Product product = productRepository.getProductById(1);
        assertThat(product)
                .isNotNull()
                .matches(p -> p.getId() == 1);
    }

    @Test
    void getProductById_ProductId_NotValid() {
        Product product;
        product = productRepository.getProductById(999);
        assertNull(product);
    }

    @Test
    void getOnSaleProductsTest_OnSale_Valid() {
        List<Product> productList = productRepository.getStockOrDiscountProducts("on_sale");
        assertThat(productList)
                .isNotEmpty()
                .extracting(Product::isOnSale)
                .allMatch(p -> p);
    }

    @Test
    void getInStockProductsTest_InStock_Valid() {
        List<Product> productList = productRepository.getStockOrDiscountProducts("in_stock");
        assertThat(productList)
                .isNotEmpty()
                .extracting(Product::isInStock)
                .allMatch(p -> p);
    }

    @Test
    void addProductTest_Product_Valid() {
        Map<String, String> productData = new TreeMap<>();
        productData.put("name", "Test product");
        productData.put("vendor", "1");
        productData.put("product_type", "1");
        productData.put("price", "10000");
        productData.put("sale_price", "5000");
        productData.put("description", "Test product");
        productData.put("in_stock", "1");
        int id = productRepository.addProduct(productData);
        Product product = productRepository.getProductById(id);
        assertThat(product)
                .isNotNull()
                .extracting(Product::getName, Product::getId, Product::getPrice, Product::getDescription)
                .contains("Test product", id, 5000, "Test product");
    }

    @Test
    void addProductTest_Product_InValid() {
        Map<String, String> productData = new TreeMap<>();
        productData.put("vendor", "1");
        productData.put("product_type", "1");
        productData.put("price", "10000");
        productData.put("sale_price", "5000");
        productData.put("description", "Test product");
        productData.put("in_stock", "1");
        int id = productRepository.addProduct(productData);
        assertEquals(-1, id);
    }

    @Test
    void updateProductTest_Product_Valid() {
        Map<String, String> productData = new TreeMap<>();
        productData.put("sale_price", "1000");
        Product product = productRepository.getProductById(1);
        product.updateData(productData);
        productRepository.updateProduct(product);
        assertEquals(1000, productRepository.getProductById(1).getPrice());
    }
}

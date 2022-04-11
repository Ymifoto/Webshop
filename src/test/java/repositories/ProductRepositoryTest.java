package repositories;

import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.repositories.AdminRepository;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.repositories.Repository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    private static final AdminRepository adminRepository = AdminRepository.getRepository();
    private static final ProductRepository productRepository = ProductRepository.getRepository();


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
        assertFalse(productRepository.productSearch("Charge").isEmpty());
    }

    @Test
    void productSearchNotValidTest() {
        assertTrue(productRepository.productSearch("xxxxxx").isEmpty());
    }

    @Test
    void getProductByIdValidTest() {
        Product product;
        product = productRepository.getProductById(1);
        assertNotNull(product);
    }

    @Test
    void getProductByIdNotValidTest() {
        Product product;
        product = productRepository.getProductById(999);
        assertNull(product);
    }

    @Test
    void getOnSaleProductsTest() {
        assertFalse(productRepository.getStockOrDiscountProducts("on_sale").isEmpty());
    }

    @Test
    void getInStockProductsTest() {
        assertFalse(productRepository.getStockOrDiscountProducts("in_stock").isEmpty());
    }

    @Test
    void addProductTest() {
        Map<String,String> productData = new TreeMap<>();
        productData.put("name", "Test product");
        productData.put("vendor", "1");
        productData.put("product_type", "1");
        productData.put("price", "10000");
        productData.put("sale_price", "5000");
        productData.put("description", "Test product");
        productData.put("in_stock", "1");
        int id = productRepository.addProduct(productData);
        assertEquals(5000,productRepository.getProductById(id).getPrice());
    }

    @Test
    void updateProductTest() {
        Map<String,String> productData = new TreeMap<>();
        productData.put("sale_price","1000");
        Product product = productRepository.getProductById(1);
        product.updateData(productData);
        productRepository.updateProduct(product);
        assertEquals(1000,productRepository.getProductById(1).getPrice());
    }

    @Test
    void addProductToCategoryTest() {
        Map<String,String> productData = new TreeMap<>();
        productData.put("sale_price","1000");
        Product product = productRepository.getProductById(1);
        product.updateData(productData);
        productRepository.updateProduct(product);
        assertEquals(1000,productRepository.getProductById(1).getPrice());
    }
}

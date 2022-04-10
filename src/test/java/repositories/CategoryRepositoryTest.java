package repositories;

import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.repositories.AdminRepository;
import hu.progmasters.webshop.repositories.CategoryRepository;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.repositories.Repository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryRepositoryTest {

    private static final AdminRepository adminRepository = AdminRepository.getRepository();
    private static final CategoryRepository categoryRepository = CategoryRepository.getRepository();
    private static final ProductRepository productRepository = ProductRepository.getRepository();

    @BeforeAll
    public static void initDataBase() {
        Repository.setTestMode(true);
        adminRepository.createTables();
        adminRepository.loadTestData();
    }

    @Test
    public void getCategeryByIdValidTest() {
        assertNotNull(categoryRepository.getCategroyById(1));
    }

    @Test
    public void getCategeryByIdNotValidTest() {
        assertNull(categoryRepository.getCategroyById(999));
    }

    @Test
    public void getCategoryListTest() {
        assertFalse(categoryRepository.getCategoryList().isEmpty());
    }

    @Test
    public void addCategoryTest() {
        Map<String, String> data = new TreeMap<>();
        data.put("category_name", "Test category");
        data.put("category_desc", "Test category description");
        int id = categoryRepository.addCategory(data);
        assertEquals("Test category",categoryRepository.getCategroyById(id).getName());
        assertEquals("Test category description",categoryRepository.getCategroyById(id).getDescription());

    }

    @Test
    public void updateCategoryTest() {
        Category category = categoryRepository.getCategroyById(1);
        category.setName("New test category");
        category.setDescription("Updated test category description");
        categoryRepository.updateCategory(category);
        assertEquals("New test category",categoryRepository.getCategroyById(1).getName());
        assertEquals("Updated test category description",categoryRepository.getCategroyById(1).getDescription());
    }

    @Test
    public void addProductToCategoryTest() {
        Category category = categoryRepository.getCategroyById(22);
        assertTrue(category.getProducts().isEmpty());
        productRepository.addProductToCategory(1,22);
        category = categoryRepository.getCategroyById(22);
        assertFalse(category.getProducts().isEmpty());
    }
}

package repositories;

import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.repositories.AdminRepository;
import hu.progmasters.webshop.repositories.CategoryRepository;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.repositories.Repository;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SoftAssertionsExtension.class)
class CategoryRepositoryTest {

    private static final AdminRepository adminRepository = AdminRepository.getRepository();
    private static final CategoryRepository categoryRepository = CategoryRepository.getRepository();
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
    void getCategeryByIdValidTest() {
        assertNotNull(categoryRepository.getCategroyById(1));
    }

    @Test
    void getCategeryByIdNotValidTest() {
        assertNull(categoryRepository.getCategroyById(999));
    }

    @Test
    void getCategoryListTest() {
        assertFalse(categoryRepository.getCategoryList().isEmpty());
    }

    @Test
    void addCategoryTest(SoftAssertions softAssertions) {
        Map<String, String> data = new TreeMap<>();
        data.put("category_name", "Test category");
        data.put("category_desc", "Test category description");
        int id = categoryRepository.addCategory(data);
        Category category = categoryRepository.getCategroyById(id);
        softAssertions.assertThat(category).extracting(Category::getName).isEqualTo("Test category");
        softAssertions.assertThat(category).extracting(Category::getDescription).isEqualTo("Test category description");
    }

    @Test
    void updateCategoryTest(SoftAssertions softAssertions) {
        Category category = categoryRepository.getCategroyById(1);
        category.setName("New test category");
        category.setDescription("Updated test category description");
        categoryRepository.updateCategory(category);
        category = categoryRepository.getCategroyById(1);
        softAssertions.assertThat(category).extracting(Category::getName).isEqualTo("New test category");
        softAssertions.assertThat(category).extracting(Category::getDescription).isEqualTo("Updated test category description");
    }

    @Test
    void addProductToCategoryTest() {
        Category category = categoryRepository.getCategroyById(22);
        assertTrue(category.getProducts().isEmpty());
        productRepository.addProductToCategory(1, 22);
        category = categoryRepository.getCategroyById(22);
        assertFalse(category.getProducts().isEmpty());
    }
}

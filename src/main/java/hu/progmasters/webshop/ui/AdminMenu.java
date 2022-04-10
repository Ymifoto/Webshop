package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.repositories.AdminRepository;
import hu.progmasters.webshop.repositories.CategoryRepository;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.ui.menuoptions.AdminMenuOptions;

import java.util.Map;
import java.util.TreeMap;

public class AdminMenu extends Menu {

    private final AdminRepository adminRepository = AdminRepository.getRepository();
    private final ProductRepository productRepository = ProductRepository.getRepository();
    private final CategoryRepository categoryRepository = CategoryRepository.getRepository();
    private Product updatingProduct;

    public void menuOptions() {
        AdminMenuOptions option;

        do {
            option = (AdminMenuOptions) getMenu(AdminMenuOptions.values());
            switch (option) {
                case ORDERS:
                    new OrderMenu().menuOptions();
                    break;
                case NEW_PRODUCT:
                    System.out.println("Add product");
                    productRepository.addProduct(getProductData(false));
                    break;
                case UPDATE_PRODUCT:
                    System.out.println("Update product");
                    System.out.print("Product ID: ");
                    updatingProduct = productRepository.getProductById(inputHandler.getInputNumber());
                    updateProduct(getProductData(true));
                    break;
                case ADD_CATEGORY:
                    System.out.print("Product id: ");
                    int productId = inputHandler.getInputNumber();
                    System.out.print("Category id: ");
                    int categoryId = inputHandler.getInputNumber();
                    productRepository.addProductToCategory(productId, categoryId);
                    break;
                case NEW_CATEGORY:
                    System.out.println("Add category");
                    categoryRepository.addCategory(getCategoryData());
                    break;
                case UPDATE_CATEGORY:
                    System.out.print("Give category ID: ");
                    Category updatingCategory = categoryRepository.getCategroyById(inputHandler.getInputNumber());
                    System.out.println(updatingCategory);
                    updatingCategory.updateData(getCategoryData());
                    categoryRepository.updateCategory(updatingCategory);
                    break;
                case CUSTOMER:
                    new CustomerMenu().menuOptions();
                    break;
                case LOAD_DATA:
                    adminRepository.loadData();
                    break;
                case DROP_TABLES:
                    adminRepository.dropTables();
                    break;
                case CREATE_TABLES:
                    adminRepository.createTables();
                    break;
                case BACK:
            }
        } while (option != AdminMenuOptions.BACK);
    }

    /** Products options */

    private Map<String, String> getProductData(boolean update) {
        Map<String, String> productData = new TreeMap<>();
        System.out.print(update ? "(" + updatingProduct.getName() + ") " : "");
        System.out.print("Give a product name: ");
        productData.put("name", inputHandler.getInputString());

        System.out.print(update ? "(" + updatingProduct.getVendor() + ") " : "");
        System.out.print("Give a vendor: ");
        productData.put("vendor", inputHandler.getInputString());

        System.out.print(update ? "(" + updatingProduct.getProductType() + ") " : "");
        System.out.print("Give a product type name: ");
        productData.put("product_type", inputHandler.getInputString());

        System.out.print(update ? "(" + updatingProduct.getBasicPrice() + ") " : "");
        System.out.print("Give a price: ");
        productData.put("price", inputHandler.getInputString());

        System.out.print(update ? "(" + updatingProduct.getSalePrice() + ") " : "");
        System.out.print("Give a sale price: ");
        productData.put("sale_price", inputHandler.getInputString());

        System.out.print(update ? "(" + updatingProduct.getDescription() + ") " : "");
        System.out.print("Short description: ");
        productData.put("description", inputHandler.getInputString());

        if (yesOrNo("The product in stock? (y/n): ")) {
            productData.put("in_stock", "1");
        } else {
            productData.put("in_stock", "0");
        }
        return productData;
    }

    private void updateProduct(Map<String, String> data) {
        if (updatingProduct != null) {
            updatingProduct.updateData(data);
            productRepository.updateProduct(updatingProduct);
        }
    }

    /** Category options */

    private Map<String, String> getCategoryData() {
        Map<String, String> categoryData = new TreeMap<>();
        System.out.print("Give a category name: ");
        categoryData.put("category_name", inputHandler.getInputString());
        System.out.print("Give a short description: ");
        categoryData.put("category_desc", inputHandler.getInputString());
        return categoryData;
    }
}

package hu.progmasters.webshop.services;

import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.repositories.AdminRepository;

import java.util.Map;
import java.util.TreeMap;

public class AdminService extends Services {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        setAdminRepository(adminRepository);
    }

    private Product updatingProduct;

    public void addProduct() {
        System.out.println("Add product");
        productRepository.addProduct(getProductData(false));
    }

    public void productUpdate() {

        System.out.println("Update product");
        System.out.print("Product ID: ");
        updatingProduct = productRepository.getProductById(InputHandler.getInputNumber());
        if (updatingProduct != null) {
            updateProduct(getProductData(true));
        } else {
            OutputHandler.outputRed("Not found product");
        }
    }

    public void addProductToCategory() {
        System.out.print("Product id: ");
        int productId = InputHandler.getInputNumber();
        System.out.print("Category id: ");
        int categoryId = InputHandler.getInputNumber();
        productRepository.addProductToCategory(productId, categoryId);
    }

    public void addNewCategory() {
        System.out.println("Add category");
        categoryRepository.addCategory(getCategoryData());
    }

    public void updateCategory() {
        System.out.print("Give category ID: ");
        Category updatingCategory = categoryRepository.getCategroyById(InputHandler.getInputNumber());
        System.out.println(updatingCategory);
        updatingCategory.updateData(getCategoryData());
        categoryRepository.updateCategory(updatingCategory);
    }

    public void loadData() {
        adminRepository.loadData();
    }

    public void dropTables() {
        adminRepository.dropTables();
    }

    public void createTables() {
        adminRepository.createTables();
    }

    private Map<String, Object> getProductData(boolean update) {
        String inputString;

        Map<String, Object> productData = new TreeMap<>();
        System.out.print(update ? "(" + updatingProduct.getName() + ") " : "");
        System.out.print("Give a product name: ");
        inputString = InputHandler.getInputString();
        productData.put("name", inputString.length() > 0 ? inputString : null);

        System.out.print(update ? "(" + updatingProduct.getVendor() + ") " : "");
        System.out.print("Give a vendor: ");
        inputString = InputHandler.getInputString();
        productData.put("vendor", inputString.length() > 0 ? inputString : null);

        System.out.print(update ? "(" + updatingProduct.getProductType() + ") " : "");
        System.out.print("Give a product type name: ");
        inputString = InputHandler.getInputString();
        productData.put("product_type", inputString.length() > 0 ? inputString : null);

        System.out.print(update ? "(" + updatingProduct.getPrice() + ") " : "");
        System.out.print("Give a price: ");
        inputString = InputHandler.getInputString();
        if (inputString.length() > 0) {
            productData.put("price", Long.parseLong(inputString) > 0 ? Long.parseLong(inputString) : null);
        }

        System.out.print(update ? "(" + updatingProduct.getSalePrice() + ") " : "");
        System.out.print("Give a sale price: ");
        inputString = InputHandler.getInputString();
        if (inputString.length() > 0) {
            productData.put("sale_price", Long.parseLong(inputString) > 0 ? Long.parseLong(inputString) : null);
        }

        System.out.print(update ? "(" + updatingProduct.getDescription() + ") " : "");
        System.out.print("Short description: ");
        inputString = InputHandler.getInputString();
        productData.put("description", inputString.length() > 0 ? inputString : null);

        if (yesOrNo("The product in stock? (y/n): ")) {
            productData.put("in_stock", true);
        } else {
            productData.put("in_stock", false);
        }
        return productData;
    }

    private void updateProduct(Map<String, Object> data) {
        if (updatingProduct != null) {
            updatingProduct.updateData(data);
            productRepository.updateProduct(updatingProduct);
        }
    }

    private Map<String, Object> getCategoryData() {
        Map<String, Object> categoryData = new TreeMap<>();
        System.out.print("Give a category name: ");
        categoryData.put("category_name", InputHandler.getInputString());
        System.out.print("Give a short description: ");
        categoryData.put("category_desc", InputHandler.getInputString());
        return categoryData;
    }
}

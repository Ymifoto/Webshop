package hu.progmasters.webshop.services;

import hu.progmasters.webshop.domain.Category;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.repositories.AdminRepository;

import java.util.Map;
import java.util.TreeMap;

public class AdminService extends Services {

    private AdminRepository adminRepository;

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
        updateProduct(getProductData(true));
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

    private Map<String, String> getProductData(boolean update) {
        Map<String, String> productData = new TreeMap<>();
        System.out.print(update ? "(" + updatingProduct.getName() + ") " : "");
        System.out.print("Give a product name: ");
        productData.put("name", InputHandler.getInputString());

        System.out.print(update ? "(" + updatingProduct.getVendor() + ") " : "");
        System.out.print("Give a vendor: ");
        productData.put("vendor", InputHandler.getInputString());

        System.out.print(update ? "(" + updatingProduct.getProductType() + ") " : "");
        System.out.print("Give a product type name: ");
        productData.put("product_type", InputHandler.getInputString());

        System.out.print(update ? "(" + updatingProduct.getBasicPrice() + ") " : "");
        System.out.print("Give a price: ");
        productData.put("price", InputHandler.getInputString());

        System.out.print(update ? "(" + updatingProduct.getSalePrice() + ") " : "");
        System.out.print("Give a sale price: ");
        productData.put("sale_price", InputHandler.getInputString());

        System.out.print(update ? "(" + updatingProduct.getDescription() + ") " : "");
        System.out.print("Short description: ");
        productData.put("description", InputHandler.getInputString());

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

    private Map<String, String> getCategoryData() {
        Map<String, String> categoryData = new TreeMap<>();
        System.out.print("Give a category name: ");
        categoryData.put("category_name", InputHandler.getInputString());
        System.out.print("Give a short description: ");
        categoryData.put("category_desc", InputHandler.getInputString());
        return categoryData;
    }
}

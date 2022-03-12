package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.domain.ShoppingCart;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.ui.menuoptions.ProductsMenuOptions;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProductsMenu extends Menu {


    private final ProductRepository productRepository = new ProductRepository();
    private final ShoppingCart shoppingCart;
    private Product updatingProduct;

    public ProductsMenu(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void menuOptions() {
        ProductsMenuOptions option;
        do {
            option = (ProductsMenuOptions) getMenu(ProductsMenuOptions.values());
            switch (option) {
                case ADD_NEW:
                    System.out.println("Add product");
                    productRepository.addProduct(getProductData(false));
                    break;
                case UPDATE:
                    System.out.println("Update product");
                        System.out.print("Product ID: ");
                        updatingProduct = productRepository.getProductById(inputHandler.getInputNumber());
                        updateProduct(getProductData(true));
                    break;
                case ON_SALE:
                    productRepository.getStockOrDiscountProducts("on_sale").forEach(System.out::println);
                    break;
                case IN_STOCK:
                    productRepository.getStockOrDiscountProducts("in_stock").forEach(System.out::println);
                    break;
                case ADD_TO_CATEGORY:
                    System.out.print("Product id: ");
                    int product = inputHandler.getInputNumber();
                    System.out.print("Category id: ");
                    int category = inputHandler.getInputNumber();
                    productRepository.addProductToCategory(product,category);
                    break;
                case SEARCH:
                    System.out.println("Product search");
                    productSearch();
                    break;
                case LIST_PRODUCT_TYPES:
                    printProductTypes();
                    break;
                case ADD_TO_CART:
                    System.out.print("Give a product id: ");
                    addProductToCart(shoppingCart,productRepository.getProductById(inputHandler.getInputNumber()));
                    break;
                case BACK:
                    break;
            }
        } while (option != ProductsMenuOptions.BACK);
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    private void productSearch() {
        System.out.println("Search products");
        System.out.print("Give a keyword: ");
        String keyword = "%" + inputHandler.getInputString() + "%";
        List<Product> founded = productRepository.productSearch(keyword);
        founded.forEach(System.out::println);
        System.out.println("Found " + founded.size() + " product");
    }

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

        if (yesOrNo("The product in stock? (yes or no): ")) {
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

    private void printProductTypes() {
        Map<Integer,String> productTypes = productRepository.getProductTypes();
        String separator = "\033[0;33m <|> \033[0m";
        int counter = 0;
        for (Map.Entry<Integer, String> entry : productTypes.entrySet()) {
            counter++;
            System.out.print("\033[0;32m" + entry.getKey() + ". " + entry.getValue() + " " + separator);
            System.out.print(counter % 4 == 0 ? System.lineSeparator() : "");
        }
    }
}

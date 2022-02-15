package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Customer;
import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.ui.menuoptions.ProductsMenuOptions;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class ProductsMenu extends Menu {


    private final ProductRepository productRepository = new ProductRepository();

    public void menuOptions() {
        ProductsMenuOptions option;
        do {
            option = (ProductsMenuOptions) getMenu(ProductsMenuOptions.values());
            switch (option) {
                case ADD_NEW:
                    System.out.println("Add product");
                    productRepository.addProduct(getProductData());
                    break;
                case UPDATE:
                    System.out.println("Update product");
                    productSearch();
                    if (yesOrNo("Update product (yes/no): ")) {
                        System.out.print("Product ID: ");
                        int id = inputHandler.getInputNumber();
                        updateProduct(id, getProductData());
                    }
                    break;
                case ONSALE:
                    productRepository.getStockOrDiscountProducts("on_sale").forEach(System.out::println);
                    break;
                case INSTOCK:
                    productRepository.getStockOrDiscountProducts("in_stock").forEach(System.out::println);
                    break;
                case SEARCH:
                    System.out.println("Product search");
                    productSearch();
                    break;
                case BACK:
                    break;
            }
        } while (option != ProductsMenuOptions.BACK);
    }

    private void productSearch() {
        System.out.println("Search products");
        System.out.print("Give a keyword: ");
        String keyword = "%" + inputHandler.getInputString() + "%";
        List<Product> founded = productRepository.productSearch(keyword);
        founded.forEach(System.out::println);
        System.out.println("Found " + founded.size() + " product");
    }

    public Map<String, String> getProductData() {
        Map<String, String> productData = new TreeMap<>();
        System.out.print("Give a product name: ");
        productData.put("name", inputHandler.getInputString());

        System.out.print("Give a vendor: ");
        productData.put("vendor", inputHandler.getInputString());

        System.out.print("Give a product type: ");
        productData.put("product_type", inputHandler.getInputString());

        System.out.print("Give a price: ");
        productData.put("price", inputHandler.getInputString());

        System.out.print("Give a sale price: ");
        productData.put("sale_price", inputHandler.getInputString());

        System.out.print("Short description: ");
        productData.put("description", inputHandler.getInputString());

        if (!yesOrNo("The product in stock? (yes or no): ")) {
            productData.put("in_stock", "1");
        } else {
            productData.put("in_stock", "0");
        }
        return productData;
    }

    private void updateProduct(int id, Map<String, String> data) {
        Product product = productRepository.getProductById(id);
        if (product != null) {
            product.updateData(data);
            productRepository.updateProduct(product);
        }
    }
}

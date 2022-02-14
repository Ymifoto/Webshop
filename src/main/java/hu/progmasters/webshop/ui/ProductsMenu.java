package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.ui.menuoptions.ProductsMenuOptions;

import java.util.List;

public class ProductsMenu extends Menu {


    private final ProductRepository productRepository = new ProductRepository();

    public void menuOptions() {
        ProductsMenuOptions option;
        do {
            option = (ProductsMenuOptions) getMenu(ProductsMenuOptions.values());
            switch (option) {
                case ADD_NEW:
                    System.out.println("Add product");
                    break;
                case UPDATE:
                    System.out.println("Update product");
                    break;
                case ONSALE:
                    productRepository.getStockOrDiscountProducts("on_sale").forEach(System.out::println);
                    break;
                case INSTOCK:
                    productRepository.getStockOrDiscountProducts("in_stock").forEach(System.out::println);
                    break;
                case SEARCH:
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
        List<Product> founded= productRepository.productSearch(keyword);
        founded.forEach(System.out::println);
        System.out.println("Found " + founded.size() + " product");
    }
}

package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.repositories.ProductRepository;
import hu.progmasters.webshop.ui.menuoptions.ProductsMenuOptions;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProductsMenu extends Menu {

    private final ProductRepository productRepository = ProductRepository.getRepository();

    public void menuOptions() {
        ProductsMenuOptions option;
        do {
            option = (ProductsMenuOptions) getMenu(ProductsMenuOptions.values());
            switch (option) {
                case ON_SALE:
                    Map<String, String> onSaleProductList = new TreeMap<>(Comparator.comparingInt(Integer::parseInt));
                    productRepository.getStockOrDiscountProducts("on_sale").forEach(product -> onSaleProductList.put(String.valueOf(product.getId()), product.getValuesForList()));
                    OutputHandler.printMapStringKey(onSaleProductList, "ID", "Products");
                    break;
                case IN_STOCK:
                    Map<String, String> inStockProductList = new TreeMap<>(Comparator.comparingInt(Integer::parseInt));
                    productRepository.getStockOrDiscountProducts("in_stock").forEach(product -> inStockProductList.put(String.valueOf(product.getId()), product.getValuesForList()));
                    OutputHandler.printMapStringKey(inStockProductList, "ID", "Products");
                    break;
                case SEARCH:
                    System.out.println("Product search");
                    productSearch();
                    break;
                case LIST_PRODUCT_TYPES:
                    OutputHandler.printList(productRepository.getProductTypes(), "Product types");
                    break;
                case ADD_TO_CART:
                    System.out.print("Give a product id: ");
                    addProductToCart(productRepository.getProductById(inputHandler.getInputNumber()));
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

        System.out.println(System.lineSeparator() + "Found " + founded.size() + " product");
        Map<String, String> productsList = new TreeMap<>();
        founded.forEach(product -> productsList.put(String.valueOf(product.getId()), product.getValuesForList()));
        OutputHandler.printMapStringKey(productsList, "ID", "Products");
    }
}

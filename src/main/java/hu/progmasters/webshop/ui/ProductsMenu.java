package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.services.ProductService;
import hu.progmasters.webshop.ui.menuoptions.ProductsMenuOptions;

public class ProductsMenu extends Menu {

    private final ProductService productService = new ProductService();


    public void menuOptions() {
        ProductsMenuOptions option;
        do {
            option = (ProductsMenuOptions) getMenu(ProductsMenuOptions.values());
            switch (option) {
                case ON_SALE:
                    productService.getOnSaleProducts();
                    break;
                case IN_STOCK:
                    productService.getInStockProducts();
                    break;
                case SEARCH:
                    productService.productSearch();
                    break;
                case LIST_PRODUCT_TYPES:
                    productService.listProductTypes();
                    break;
                case ADD_TO_CART:
                    productService.addToCart(shoppingCart);
                    break;
                case BACK:
                    break;
            }
        } while (option != ProductsMenuOptions.BACK);
    }
}

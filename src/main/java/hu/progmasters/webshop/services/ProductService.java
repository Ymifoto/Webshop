package hu.progmasters.webshop.services;

import hu.progmasters.webshop.domain.Product;
import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.handlers.OutputHandler;
import hu.progmasters.webshop.repositories.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProductService extends Services {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        setProductRepository(productRepository);
    }

    public void getOnSaleProducts() {
        Map<String, String> onSaleProductList = new TreeMap<>(Comparator.comparingInt(Integer::parseInt));
        productRepository.getStockOrDiscountProducts("on_sale").forEach(product -> onSaleProductList.put(String.valueOf(product.getId()), product.getValuesForList()));
        OutputHandler.printMapStringKey(onSaleProductList, "ID", "Products");
    }

    public void getInStockProducts() {
        Map<String, String> inStockProductList = new TreeMap<>(Comparator.comparingInt(Integer::parseInt));
        productRepository.getStockOrDiscountProducts("in_stock").forEach(product -> inStockProductList.put(String.valueOf(product.getId()), product.getValuesForList()));
        OutputHandler.printMapStringKey(inStockProductList, "ID", "Products");
    }

    public void productSearch() {
        System.out.println("Search products");
        System.out.print("Give a keyword: ");
        String keyword = "%" + InputHandler.getInputString() + "%";
        List<Product> founded = productRepository.productSearch(keyword);

        System.out.println(System.lineSeparator() + "Found " + founded.size() + " product");
        Map<String, String> productsList = new TreeMap<>();
        founded.forEach(product -> productsList.put(String.valueOf(product.getId()), product.getValuesForList()));
        OutputHandler.printMapStringKey(productsList, "ID", "Products");
    }

    public void listProductTypes() {
        OutputHandler.printList(productRepository.getProductTypes(), "Product types");
    }

}

package hu.progmasters.webshop;

import hu.progmasters.webshop.domain.WebShop;
import hu.progmasters.webshop.handlers.LogHandler;

public class Main {

    public static void main(String[] args) {

        WebShop webShop = new WebShop();

        webShop.getProductRepository().getDiscountProducts().forEach(System.out::println);

    }
}

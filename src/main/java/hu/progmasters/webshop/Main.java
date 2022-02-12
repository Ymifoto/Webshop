package hu.progmasters.webshop;

import hu.progmasters.webshop.domain.*;
import hu.progmasters.webshop.ui.menuoptions.MainMenu;
import hu.progmasters.webshop.ui.MenuBuilder;

public class Main {

    public static void main(String[] args) {

        WebShop webShop = new WebShop();

        MenuBuilder menuBuilder = new MenuBuilder();

        menuBuilder.startMenu();

       // webShop.getProductRepository().getDiscountProducts().forEach(System.out::println);




    }
}

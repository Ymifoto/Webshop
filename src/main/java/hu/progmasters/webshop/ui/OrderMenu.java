package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.repositories.OrderRepository;
import hu.progmasters.webshop.ui.menuoptions.OrdersMenuOptions;

public class OrderMenu extends Menu {


    private final OrderRepository orderRepository = new OrderRepository();

    public OrderMenu() {
    }

    public void menuOptions() {
        OrdersMenuOptions option;
        do {
            option = (OrdersMenuOptions) getMenu(OrdersMenuOptions.values());
            switch (option) {
                case NEW_ORDER:
                    System.out.println("New order");
                case ALL_ORDERS:
                    orderRepository.getAllOrders().forEach(System.out::println);
                    break;
                case SEARCH:
                    System.out.print("Give a keyword: ");
                    orderRepository.orderSearch("%" + inputHandler.getInputString() + "%").forEach(System.out::println);
                    break;
                case BACK:
                    break;
            }
        } while (option != OrdersMenuOptions.BACK);
    }
}

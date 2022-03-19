package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.repositories.OrderRepository;
import hu.progmasters.webshop.ui.menuoptions.OrdersMenuOptions;

public class OrderMenu extends Menu {

    private final OrderRepository orderRepository = OrderRepository.getRepository();

    public void menuOptions() {
        OrdersMenuOptions option;
        do {
            option = (OrdersMenuOptions) getMenu(OrdersMenuOptions.values());
            switch (option) {
                case IN_PROGRESS_ORDERS:
                    orderRepository.getInProgressOrders().forEach(System.out::println);
                    break;
                case ALL_ORDERS:
                    orderRepository.getAllOrders().forEach(System.out::println);
                    break;
                case SEARCH:
                    System.out.print("Give a keyword: ");
                    orderRepository.orderSearch("%" + inputHandler.getInputString() + "%").forEach(System.out::println);
                    break;
                case SET_SHIPPED:
                    System.out.print("Give a order ID: ");
                    orderRepository.setOrderShippedDone(inputHandler.getInputNumber());
                    break;
                case BACK:
            }
        } while (option != OrdersMenuOptions.BACK);
    }
}

package hu.progmasters.webshop.ui;

import hu.progmasters.webshop.repositories.OrderRepository;
import hu.progmasters.webshop.services.OrderService;
import hu.progmasters.webshop.ui.menuoptions.OrdersMenuOptions;

public class OrderMenu extends Menu {

    private final OrderService orderService = new OrderService(new OrderRepository());

    public void menuOptions() {
        OrdersMenuOptions option;
        do {
            option = (OrdersMenuOptions) getMenu(OrdersMenuOptions.values());
            switch (option) {
                case IN_PROGRESS_ORDERS:
                    orderService.getInProgressOrders();
                    break;
                case ALL_ORDERS:
                    orderService.getAllOrders();
                    break;
                case SEARCH:
                    orderService.orderSearch();
                    break;
                case SET_SHIPPED:
                    orderService.setOrderShipped();
                    break;
                case BACK:
            }
        } while (option != OrdersMenuOptions.BACK);
    }
}

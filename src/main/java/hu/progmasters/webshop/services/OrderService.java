package hu.progmasters.webshop.services;

import hu.progmasters.webshop.handlers.InputHandler;
import hu.progmasters.webshop.repositories.OrderRepository;

public class OrderService extends Services {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        setOrderRepository(orderRepository);
    }

    public void getInProgressOrders() {
        orderRepository.getInProgressOrders().forEach(System.out::println);
    }

    public void getAllOrders() {
        orderRepository.getAllOrders().forEach(System.out::println);
    }

    public void orderSearch() {
        System.out.print("Give a keyword: ");
        orderRepository.orderSearch("%" + InputHandler.getInputString() + "%").forEach(System.out::println);
    }

    public void setOrderShipped() {
        System.out.print("Give a order ID: ");
        orderRepository.setOrderShipped(InputHandler.getInputNumber());
    }
}

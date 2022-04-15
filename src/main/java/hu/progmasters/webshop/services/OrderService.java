package hu.progmasters.webshop.services;

import hu.progmasters.webshop.handlers.InputHandler;

public class OrderService extends Services {

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

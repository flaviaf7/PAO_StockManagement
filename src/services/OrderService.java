package services;
import model.order.Order;

import java.util.*;

public class OrderService {
    private Set<Order> orders;

    public OrderService(List<Order> orders) {
        this.orders = new HashSet<Order>();
        for (Order order : orders) {
            this.orders.add(order);
        }
    }

    public OrderService() {
        this.orders = new HashSet<Order>();
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
    }

    public Order getOrderById(Integer id) {
        for (Order order : this.orders) {
            if (order.getOrderID().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void listAllOrders() {
        for (Order order : this.orders) {
            System.out.println(order.toString());
        }
    }

    public void placeOrder(Order order) {
        addOrder(order);
        System.out.println("Order placed successfully!");
    }
}

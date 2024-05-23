package services;
import model.Distributor;
import model.order.Order;
import model.order.OrderStatus;

import java.util.*;

public class OrderService {
    private Set<Order> orders;

    public OrderService(List<Order> orders) {
        this.orders = new HashSet<Order>();
        this.orders.addAll(orders);
    }

    public OrderService() {
        this.orders = new HashSet<Order>();
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void updateOrder(Scanner scanner, Distributor connectedDistributor) {
        System.out.print("Enter order ID to update: ");
        int orderIdToUpdate = scanner.nextInt();
        scanner.nextLine();
        Order orderToUpdate = getOrderById(orderIdToUpdate, connectedDistributor);
        if (orderToUpdate != null) {
            System.out.println("Current Order Status: " + orderToUpdate.getOrderStatus());
            System.out.println("Enter new status (PENDING, ACCEPTED, REJECTED, DELIVERED): ");
            String newStatus = scanner.nextLine();
            try {
                orderToUpdate.setOrderStatus(OrderStatus.valueOf(newStatus.toUpperCase()));
                System.out.println("Order status updated successfully.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid order status. Please enter a valid status.");
            }
        } else {
            System.out.println("Order not found.");
        }
    }

    public void removeOrder(Scanner scanner, Distributor connectedDistributor) {
        System.out.print("Enter order ID to delete: ");
        int orderIdToDelete = scanner.nextInt();
        scanner.nextLine();
        Order orderToDelete = getOrderById(orderIdToDelete, connectedDistributor);
        if (orderToDelete != null) {
            this.orders.remove(orderToDelete);
            System.out.println("Order deleted successfully.");
        } else {
            System.out.println("Order not found.");
        }
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

    public Order getOrderById(Integer id, Distributor connectedDistributor) {
        for (Order order : this.orders) {
            if (order.getOrderID().equals(id) && order.getDistributor().equals(connectedDistributor)) {
                return order;
            }
        }
        return null;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void listAllOrders() {
        if (orders == null || orders.isEmpty()){
            System.out.println("No orders found.");
        }
        else {
            for (Order order : orders) {
                System.out.println(order.toString());
            }
        }
    }

    public void listAllOrders(Distributor connectedDistributor) {
        boolean foundOrders = false;
        for (Order order : orders) {
            if (order.getDistributor().equals(connectedDistributor)) {
                System.out.println(order.toString());
                foundOrders = true;
            }
        }
        if (!foundOrders) {
            System.out.println("No orders found for the connected distributor.");
        }
    }

}

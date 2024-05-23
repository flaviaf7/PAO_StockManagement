package services;

import database.JdbcConnection;
import exception.DistributorNotFoundException;
import model.Distributor;
import model.Product.Product;
import model.Product.ProductInterface;
import model.order.Order;
import model.order.OrderStatus;

import java.sql.*;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

public class OrderService {
    private final Connection connection;

    public OrderService() {
        this.connection = JdbcConnection.getConnection();
    }

    public void addOrder(Order order) {
        String query = "INSERT INTO Orders (distributorID, orderStatus, price) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, order.getDistributor().getID());
            preparedStatement.setString(2, order.getOrderStatus().toString());
            preparedStatement.setDouble(3, order.getPrice());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrderID(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrder(Scanner scanner, Distributor connectedDistributor) {
        System.out.print("Enter order ID to update: ");
        int orderIdToUpdate = scanner.nextInt();
        scanner.nextLine();
        Order orderToUpdate = getOrderById(orderIdToUpdate, connectedDistributor);
        if (orderToUpdate != null) {
            System.out.println("Current Order Status: " + orderToUpdate.getOrderStatus());
            System.out.println("Enter new status (PENDING, ACCEPTED, REJECTED, DELIVERED): ");
            String newStatus = scanner.nextLine().toUpperCase();
            try {
                OrderStatus status = OrderStatus.valueOf(newStatus);
                updateOrderStatus(orderToUpdate, status);
                System.out.println("Order status updated successfully.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid order status. Please enter a valid status.");
            }
        } else {
            System.out.println("Order not found.");
        }
    }

    private void updateOrderStatus(Order orderToUpdate, OrderStatus newStatus) {
        String query = "UPDATE Orders SET orderStatus = ? WHERE orderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newStatus.toString());
            preparedStatement.setInt(2, orderToUpdate.getOrderID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeOrder(Scanner scanner, Distributor connectedDistributor) {
        System.out.print("Enter order ID to delete: ");
        int orderIdToDelete = scanner.nextInt();
        scanner.nextLine();
        Order orderToDelete = getOrderById(orderIdToDelete, connectedDistributor);
        if (orderToDelete != null) {
            deleteOrder(orderToDelete);
            System.out.println("Order deleted successfully.");
        } else {
            System.out.println("Order not found.");
        }
    }

    private void deleteOrder(Order orderToDelete) {
        String query = "DELETE FROM Orders WHERE orderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderToDelete.getOrderID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order getOrderById(Integer id, Distributor connectedDistributor) {
        String query = "SELECT * FROM Orders WHERE orderID = ? AND distributorID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, connectedDistributor.getID());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = extractOrderFromResultSet(resultSet, connectedDistributor);
                    order.setProducts(getProductsForOrder(order.getOrderID()));
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Order getOrderById(Integer id) {
        String query = "SELECT * FROM Orders WHERE orderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Distributor distributor = new DistributorService().getDistributorById(resultSet.getInt("distributorID"));
                    Order order = extractOrderFromResultSet(resultSet, distributor);
                    order.setProducts(getProductsForOrder(order.getOrderID()));
                    return order;
                }
            } catch (DistributorNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Set<Order> getOrders(Distributor connectedDistributor) {
        Set<Order> orders = new HashSet<>();
        String query = "SELECT * FROM Orders WHERE distributorID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, connectedDistributor.getID());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = extractOrderFromResultSet(resultSet, connectedDistributor);
                    order.setProducts(getProductsForOrder(order.getOrderID()));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Set<Order> getAllOrders() {
        Set<Order> orders = new HashSet<>();
        String query = "SELECT * FROM Orders";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Order order = extractOrderFromResultSet(resultSet);
                order.setProducts(getProductsForOrder(order.getOrderID()));
                orders.add(order);
            }
        } catch (SQLException | DistributorNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private Order extractOrderFromResultSet(ResultSet resultSet, Distributor connectedDistributor) throws SQLException {
        int orderId = resultSet.getInt("orderID");
        OrderStatus status = OrderStatus.valueOf(resultSet.getString("orderStatus"));
        Double price = resultSet.getDouble("price");
        return new Order(orderId, connectedDistributor, status, price);
    }

    private Order extractOrderFromResultSet(ResultSet resultSet) throws SQLException, DistributorNotFoundException {
        int orderId = resultSet.getInt("orderID");
        int distributorId = resultSet.getInt("distributorID");
        OrderStatus status = OrderStatus.valueOf(resultSet.getString("orderStatus"));
        Double price = resultSet.getDouble("price");
        Distributor distributor = new DistributorService().getDistributorById(distributorId);
        return new Order(orderId, distributor, status, price);
    }

    private Hashtable<ProductInterface, Integer> getProductsForOrder(int orderId) {
        Hashtable<ProductInterface, Integer> products = new Hashtable<>();
        return products;
    }



    public void listAllOrders() {
        Set<Order> orders = getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }

    public void listAllOrders(Distributor connectedDistributor) {
        Set<Order> orders = getOrders(connectedDistributor);
        if (orders.isEmpty()) {
            System.out.println("No orders found for the connected distributor.");
        } else {
            for (Order order : orders) {
                System.out.println(order);
            }
        }

    }


    public Set<Order> getOrders() {
        return getAllOrders();
    }

}

package model;
import model.Product.*;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    final private String orderID;
    final private LocalDateTime orderDate;
    private Distributor distributor;
    final private List<Product> productList;
    private String orderStatus;
    final private double totalAmount;
    private LocalDateTime deliveryDate;

    public Order(String orderID, Distributor distributor, List<Product> productList,
                 String orderStatus, double totalAmount) throws Exception {

        if (productList.isEmpty())
            throw new Exception("There aren't any products on your order!");

        this.orderID = orderID;
        this.orderDate = LocalDateTime.now();
        this.distributor = distributor;
        this.productList = productList;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", orderDate=" + orderDate +
                ", distributor=" + distributor +
                ", productList=" + productList +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalAmount=" + totalAmount +
                ", deliveryDate=" + deliveryDate +
                '}';
    }

    public Integer getDistributorId(){
        return this.distributor.getID();
    }
    public String getOrderID() {
        return orderID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}

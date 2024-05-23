package model.order;
import model.Distributor;
import model.Product.*;

import java.time.LocalDateTime;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

public class Order {
    final private Integer orderID;
    final private LocalDateTime orderDate;
    private Distributor distributor;
    private Hashtable<ProductInterface, Integer> products;
    private OrderStatus orderStatus;
    private Double price;
    private LocalDateTime deliveryDate;

    public Order(Integer orderID, Distributor distributor, Hashtable<ProductInterface, Integer> products,
                 OrderStatus orderStatus, Double price){

        this.orderID = orderID;
        this.orderDate = LocalDateTime.now();
        this.distributor = distributor;
        this.products = products;
        this.orderStatus = orderStatus;
        this.price = price;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderID, order.orderID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID);
    }

    public Integer getDistributorId(){
        return this.distributor.getID();
    }
    public Integer getOrderID() {
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

    public Dictionary<ProductInterface, Integer> getProductList(){ return products; }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", orderDate=" + orderDate +
                ", distributor=" + distributor +
                ", productList=" + products +
                ", orderStatus='" + orderStatus + '\'' +
                ", totalAmount=" + price +
                ", deliveryDate=" + deliveryDate +
                "}\n";
    }

}

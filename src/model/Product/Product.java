package model.Product;
import model.Order;

public abstract class Product {
    protected int product_id;
    protected float price;
    protected Order order;
    protected String name;

    public float getPrice(){
        return price;
    }

    public Order getOrder(){
        return order;
    }

    public String getName(){
        return name;
    }

    public int getProduct_id(){
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setName(String name) {
        this.name = name;
    }
}

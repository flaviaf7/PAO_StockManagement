package model.Product;
import model.Product.*;
import model.order.*;
import java.util.*;

public class Product implements ProductInterface{
    protected int product_id;
    protected Double price;
    protected String name;

    public Product(){}
    public Product(String name, Double price){
        this.name = name;
        this.price = price;
    }

    public Double getPrice(){
        return price;
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package model.Product;
import model.order.*;

public interface ProductInterface {
    public Double getPrice();
    public String getName();
    public int getProduct_id();
    public void setProduct_id(int product_id);
    public void setPrice(Double price);
    public void setName(String name);
}

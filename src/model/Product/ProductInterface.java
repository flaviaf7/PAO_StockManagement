package model.Product;

public interface ProductInterface {
    Double getPrice();
    String getName();
    int getProduct_id();
    void setProduct_id(int product_id);
    void setPrice(Double price);
    void setName(String name);
    @Override
    String toString();
}

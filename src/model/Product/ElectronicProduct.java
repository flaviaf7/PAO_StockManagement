package model.Product;

public class ElectronicProduct extends Product{
    private String brand;
    private String model;

    public ElectronicProduct(int productId, String name, Double price, String brand, String model){
        super(productId, name, price);
        this.brand = brand;
        this.model = model;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "ElectronicProduct{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", product_id=" + product_id +
                ", price=" + price +
                ", name='" + name + '\'' +
                "}\n";
    }
}

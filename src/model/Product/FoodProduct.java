package model.Product;

public class FoodProduct extends Product{
    private String expirationDate;
    private String allergens;

    public FoodProduct(String name, Double price, String expirationDate, String allergens) {
        super(name, price);
        this.expirationDate = expirationDate;
        this.allergens = allergens;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    @Override
    public String toString() {
        return "FoodProduct{" +
                "expirationDate='" + expirationDate + '\'' +
                ", allergens='" + allergens + '\'' +
                ", product_id=" + product_id +
                ", price=" + price +
                ", name='" + name + '\'' +
                "}\n";
    }
}

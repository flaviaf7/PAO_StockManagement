package model;
import model.Product.*;

import java.util.*;

public class Distributor {
    private Integer ID;
    private String name;
    private String emailAddress;
    private Address address;
    private String password;
    private List<ProductInterface> productsSupplied;

    public Distributor(String name, String emailAddress, Address address, String password, List<ProductInterface> productsSupplied) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.address = address;
        this.password = password;
        this.productsSupplied = productsSupplied;
    }

    public Distributor(Integer distributorId, String name, String emailAddress, Address address, String password, List<ProductInterface> productsSupplied) {
        this.ID = distributorId;
        this.name = name;
        this.emailAddress = emailAddress;
        this.address = address;
        this.password = password;
        this.productsSupplied = productsSupplied;
    }

    public Distributor(Integer ID, String name, String emailAddress, Address address, String password) {
        this.ID = ID;
        this.name = name;
        this.emailAddress = emailAddress;
        this.address = address;
        this.password = password;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distributor that = (Distributor) o;
        return Objects.equals(ID, that.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Address getAddress() {
        return address;
    }

    public String getPassword() { return password; }
    public List<ProductInterface> getProductsSupplied() {
        return productsSupplied;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setProductsSupplied(List<ProductInterface> productsSupplied) {
        this.productsSupplied = productsSupplied;
    }

    public void addProduct(Product product) {
        this.productsSupplied.add(product);
    }

    @Override
    public String toString() {
        return "Distributor{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", address=" + address +
                ", productsSupplied=" + productsSupplied +
                "}\n";
    }
}

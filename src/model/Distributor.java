package model;
import model.Product.*;

import java.util.*;

public class Distributor {
    private Integer ID;
    private String name;
    private String emailAddress;
    private Address address;
    private List<ProductInterface> productsSupplied;

    public Distributor(String name, String emailAddress, Address address, List<ProductInterface> productsSupplied) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.address = address;
        this.productsSupplied = productsSupplied;
    }

    public Distributor(Integer distributorId, String name, String emailAddress, Address address, List<ProductInterface> productsSupplied) {
        this.ID = distributorId;
        this.name = name;
        this.emailAddress = emailAddress;
        this.address = address;
        this.productsSupplied = productsSupplied;
    }


    private void read(Scanner in){
        System.out.println("Distributor Name: ");
        this.name = in.nextLine();
        System.out.println("Email address: ");
        this.emailAddress = in.nextLine();
        System.out.println("Address Details: ");
        Address distributorAddress = new Address();
        distributorAddress.read(in);
        this.address = distributorAddress;
        this.productsSupplied = null;
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
}

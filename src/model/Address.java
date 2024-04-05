package model;
import java.util.Scanner;
public class Address {
    private int ID;
    private String street, city, county, postalCode, country;

    public Address(){}
    public Address(String street, String city, String county, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.county = county;
        this.postalCode = postalCode;
        this.country = country;
    }

    public Address(Scanner in){
        this.read(in);
    }

    public void read(Scanner in){
        System.out.println("Street: ");
        this.street = in.nextLine();
        System.out.println("City: ");
        this.city = in.nextLine();
        System.out.println("County: ");
        this.county = in.nextLine();
        System.out.println("Postal code: ");
        this.postalCode = in.nextLine();
        System.out.println("Country: ");
        this.country = in.nextLine();
    }

    @Override
    public String toString() {
        return "{address_id = " + ID + ", street = " + street +  ", county = " + county + ", postalCode = " + postalCode + ", country = " + country + '}';
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}

package main;

import exception.DistributorNotFoundException;
import model.Address;
import model.Distributor;
import model.DistributorFactory;
import model.Product.ElectronicProduct;
import model.Product.FoodProduct;
import model.Product.ProductFactory;
import model.Product.ProductInterface;
import services.DistributorService;
import services.ProductService;
import services.OrderService;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws DistributorNotFoundException {
        Address address1 = new Address("Street 1", "City 1", "County 1", "12345", "Country 1");
        Address address2 = new Address("Street 2", "City 2", "County 2", "23456", "Country 2");
        Address address3 = new Address("Street 3", "City 3", "County 3", "34567", "Country 3");

        ElectronicProduct electronicProduct1 = ProductFactory.createElectronicProduct("Laptop", 1200.0, "Brand 1", "Model 1");
        ElectronicProduct electronicProduct2 = ProductFactory.createElectronicProduct("Smartphone", 800.0, "Brand 2", "Model 2");

        FoodProduct foodProduct1 = ProductFactory.createFoodProduct("Chocolate", 2.5, "2024-12-31", "Milk, Soy");
        FoodProduct foodProduct2 = ProductFactory.createFoodProduct("Bread", 1.0, "2024-06-01", "Wheat");

        ProductInterface normalProduct = ProductFactory.createProductItem("Book", 15.0);

        List<ProductInterface> products1 = new ArrayList<>();
        products1.add(electronicProduct1);
        products1.add(electronicProduct2);

        List<ProductInterface> products2 = new ArrayList<>();
        products2.add(foodProduct1);
        products2.add(foodProduct2);

        List<ProductInterface> products3 = new ArrayList<>();
        products3.add(normalProduct);

        Distributor distributor1 = DistributorFactory.createDistributor("Distributor 1", "email1@example.com", address1, "distributor1", products1);
        Distributor distributor2 = DistributorFactory.createDistributor("Distributor 2", "email2@example.com", address2, "distributor2", products2);
        Distributor distributor3 = DistributorFactory.createDistributor("Distributor 3", "email3@example.com", address3, "distributor3", products3);

        Scanner scanner = new Scanner(System.in);
        DistributorService distributorService = new DistributorService();
        OrderService orderService = new OrderService();
        ProductService productService = new ProductService();

        distributorService.addDistributor(distributor1);
        distributorService.addDistributor(distributor2);
        distributorService.addDistributor(distributor3);

        MainService mainService = MainService.init(orderService, distributorService, productService, scanner);
        mainService.initEmployeeItems();
        mainService.mainLoop();

        scanner.close();
    }
}
package main;

import database.SeedData;
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

        Scanner scanner = new Scanner(System.in);
        OrderService orderService = new OrderService();
        ProductService productService = new ProductService();
        DistributorService distributorService = new DistributorService();

        SeedData seed = new SeedData(distributorService, productService, orderService);

        seed.populateIfNeeded();



        MainService mainService = MainService.init(orderService, distributorService, productService, scanner);
        mainService.initEmployeeItems();
        mainService.mainLoop();

        scanner.close();
    }
}
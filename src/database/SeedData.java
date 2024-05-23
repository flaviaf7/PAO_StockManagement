package database;

import model.Address;
import model.Distributor;
import model.DistributorFactory;
import model.Product.ElectronicProduct;
import model.Product.FoodProduct;
import model.Product.ProductFactory;
import model.Product.ProductInterface;
import model.order.Order;
import model.order.OrderStatus;
import services.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SeedData {
    private final DistributorService distributorService;
    private final ProductService productService;
    private final OrderService orderService;

    public SeedData(DistributorService distributorService, ProductService productService, OrderService orderService) {
        this.distributorService = distributorService;
        this.productService = productService;
        this.orderService = orderService;
    }

    public void populateIfNeeded() {
        populateAddressTable();
        populateProductTable();
        populateDistributorTable();
        populateOrdersTable();
    }

    private void populateAddressTable() {
        if (distributorService.getAllAddresses().isEmpty()) {
            Address address1 = new Address("Street 1", "City 1", "County 1", "12345", "Country 1");
            Address address2 = new Address("Street 2", "City 2", "County 2", "23456", "Country 2");
            Address address3 = new Address("Street 3", "City 3", "County 3", "34567", "Country 3");

            distributorService.addAddress(address1);
            distributorService.addAddress(address2);
            distributorService.addAddress(address3);
        }
    }

    private void populateDistributorTable() {
        if (distributorService.getDistributors().isEmpty()) {
            List<Address> addresses = new ArrayList<>(distributorService.getAllAddresses());
            List<ProductInterface> products = new ArrayList<>(productService.getAllProducts());

            List<ProductInterface> products1 = new ArrayList<>(products).subList(0, 2);
            List<ProductInterface> products2 = new ArrayList<>(products).subList(1, 4);
            List<ProductInterface> products3 = new ArrayList<>(products).subList(3, 4);

            Distributor distributor1 = DistributorFactory.createDistributor("Distributor 1", "email1@example.com", addresses.get(0), "password1", products1);
            Distributor distributor2 = DistributorFactory.createDistributor("Distributor 2", "email2@example.com", addresses.get(1), "password2", products2);
            Distributor distributor3 = DistributorFactory.createDistributor("Distributor 3", "email3@example.com", addresses.get(2), "password3", products3);

            distributorService.addDistributor(distributor1);
            distributorService.addDistributor(distributor2);
            distributorService.addDistributor(distributor3);
        }
    }

    private void populateProductTable() {
        if (productService.getAllProducts().isEmpty()) {
            ElectronicProduct electronicProduct1 = ProductFactory.createElectronicProduct("Laptop", 1200.0, "Brand 1", "Model 1");
            ElectronicProduct electronicProduct2 = ProductFactory.createElectronicProduct("Smartphone", 800.0, "Brand 2", "Model 2");
            FoodProduct foodProduct1 = ProductFactory.createFoodProduct("Chocolate", 2.5, "2024-12-31", "Milk, Soy");
            FoodProduct foodProduct2 = ProductFactory.createFoodProduct("Bread", 1.0, "2024-06-01", "Wheat");

            productService.addProduct(electronicProduct1);
            productService.addProduct(electronicProduct2);
            productService.addProduct(foodProduct1);
            productService.addProduct(foodProduct2);

            Distributor distributor1 = distributorService.getDistributors().stream().findFirst().orElse(null);
            Distributor distributor2 = distributorService.getDistributors().stream().skip(1).findFirst().orElse(null);

            if (distributor1 != null && distributor2 != null) {
                productService.addProductToDistributor(electronicProduct1, distributor1);
                productService.addProductToDistributor(electronicProduct2, distributor1);
                productService.addProductToDistributor(foodProduct1, distributor2);
                productService.addProductToDistributor(foodProduct2, distributor2);
            }
        }
    }

    private void populateOrdersTable() {
        if (orderService.getOrders().isEmpty()) {
            Distributor distributor1 = distributorService.getDistributors().stream().findFirst().orElse(null);
            Distributor distributor2 = distributorService.getDistributors().stream().skip(1).findFirst().orElse(null);
            Distributor distributor3 = distributorService.getDistributors().stream().skip(2).findFirst().orElse(null);

            HashSet<ProductInterface> productsSet = productService.getAllProducts();
            List<ProductInterface> products = new ArrayList<>(productsSet);

            if (!products.isEmpty()) {
                ProductInterface product1 = products.get(0);
                ProductInterface product2 = products.size() > 1 ? products.get(1) : null;

                if (distributor1 != null && distributor2 != null && distributor3 != null && product1 != null) {
                    Order order1 = new Order(1, distributor1, OrderStatus.PENDING, product1.getPrice());
                    orderService.addOrder(order1);

                    if (product2 != null) {
                        Order order2 = new Order(2, distributor2, OrderStatus.ACCEPTED, product2.getPrice());
                        orderService.addOrder(order2);
                    }

                    Order order3 = new Order(3, distributor3, OrderStatus.REJECTED, product1.getPrice());
                    orderService.addOrder(order3);
                }
            }
        }
    }
}
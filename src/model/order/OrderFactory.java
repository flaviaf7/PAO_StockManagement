package model.order;
import model.*;
import model.Product.*;

import java.util.*;

public class OrderFactory {
    static Integer orderId = 0;

    public static Order createOrder(Distributor distributor, Hashtable<ProductInterface, Integer> products, OrderStatus orderStatus, Double price) {
        return new Order(++orderId, distributor, products, orderStatus, price);
    }

    public static Order createOrder(Scanner scanner) {
        Distributor distributor;
        Double price;
        orderId++;
        Hashtable<ProductInterface, Integer> products = new Hashtable<>();

        System.out.println("Enter distributor details:");
        distributor = DistributorFactory.createDistributor(scanner);

        System.out.print("Enter price: ");
        price = scanner.nextDouble();

        System.out.print("Enter number of products: ");
        int productCount = scanner.nextInt();

        for (int i = 0; i < productCount; i++) {
            System.out.print("Enter product type (1 - ProductItem, 2 - FoodProduct, 3 - ElectronicProduct): ");
            int productType = scanner.nextInt();
            int quantity = scanner.nextInt();
            switch (productType) {
                case 1:
                    Product productItem = ProductFactory.createProduct(scanner);
                    System.out.print("Enter quantity: ");
                    products.put(productItem, quantity);
                    break;
                case 2:
                    FoodProduct foodProduct = ProductFactory.createFoodProduct(scanner);
                    System.out.print("Enter quantity: ");
                    quantity = scanner.nextInt();
                    products.put(foodProduct, quantity);
                    break;
                case 3:
                    ElectronicProduct electronicProduct = ProductFactory.createElectronicProduct(scanner);
                    System.out.print("Enter quantity: ");
                    quantity = scanner.nextInt();
                    products.put(electronicProduct, quantity);
                    break;
                default:
                    System.out.println("Invalid product type.");
                    break;
            }
        }

        return new Order(orderId, distributor, products, OrderStatus.ACCEPTED, price);
    }
}

package model;

import model.Product.*;

import java.util.*;

public class DistributorFactory {
    static private Integer ID = 0;

    public static Distributor createDistributor(String name, String emailAddress, Address address, List<ProductInterface> products) {
        return new Distributor(++ID, name, emailAddress, address, products);
    }

    public static Distributor createDistributor(Scanner scanner) {
        String name;
        String emailaddress;
        Address address;
        List<ProductInterface> products = new ArrayList<ProductInterface>();

        System.out.print("Enter distributor name: ");
        name = scanner.next();

        System.out.print("Enter distributor email address: ");
        emailaddress = scanner.next();

        System.out.println("Enter distributor address");
        address = new Address();
        address.read(scanner);

        System.out.print("Enter number of products: ");
        int productCount = scanner.nextInt();

        for (int i = 0; i < productCount; i++) {
            System.out.print("Enter product type (1 - ElectronicProduct, 2 - FoodProduct, 3 - Menu): ");
            int productType = scanner.nextInt();
            switch (productType) {
                case 1:
                    products.add(ProductFactory.createElectronicProduct(scanner));
                    break;
                case 2:
                    products.add(ProductFactory.createFoodProduct(scanner));
                    break;
                default:
                    System.out.println("Invalid product type");
                    break;
            }
        }

        return new Distributor(++ID, name, emailaddress, address, products);
    }
}

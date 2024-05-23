package model.Product;

import java.util.Scanner;

public class ProductFactory {
    private static Integer ID = 0;

    public static FoodProduct createFoodProduct(String name, Double price, String expirationDate, String allergens) {
        ID++;
        return new FoodProduct(ID, name, price, expirationDate, allergens);
    }

    public static ElectronicProduct createElectronicProduct(String name, Double price, String brand, String model) {
        ID++;
        return new ElectronicProduct(ID, name, price, brand, model);
    }

    public static Product createProductItem(String name, Double price) {
        ID++;
        return new Product(ID, name, price);
    }


    public static Product createProduct(Scanner scanner) {
        String name;
        Double price;
        int productType;

        System.out.println("Select product type:");
        System.out.println("1. ProductItem");
        System.out.println("2. FoodProduct");
        System.out.println("3. ElectronicProduct");
        System.out.print("Enter product type (1-3): ");
        productType = scanner.nextInt();

        switch (productType) {
            case 1:
                System.out.print("Enter product name: ");
                name = scanner.next();

                System.out.print("Enter product price: ");
                price = scanner.nextDouble();

                return createProductItem(name, price);

            case 2:
                return createFoodProduct(scanner);

            case 3:
                return createElectronicProduct(scanner);

            default:
                System.out.println("Invalid product type.");
                return null;
        }
    }


    public static ElectronicProduct createElectronicProduct(Scanner scanner) {
        String name;
        Double price;
        String brand;
        String model;
        ID++;

        System.out.print("Enter product name: ");
        name = scanner.next();

        System.out.print("Enter product price: ");
        price = scanner.nextDouble();

        System.out.print("Enter product brand: ");
        brand = scanner.next();

        System.out.print("Enter product model: ");
        model = scanner.next();

        return new ElectronicProduct(ID, name, price, brand, model);
    }


    public static FoodProduct createFoodProduct(Scanner scanner) {
        String name;
        Double price;
        String expirationDate;
        String allergens;
        ID++;

        System.out.print("Enter product name: ");
        name = scanner.next();

        System.out.print("Enter product price: ");
        price = scanner.nextDouble();

        System.out.print("Enter expiration date: ");
        expirationDate = scanner.next();

        System.out.print("Enter allergens: ");
        allergens = scanner.next();

        return new FoodProduct(ID, name, price, expirationDate, allergens);
    }
}

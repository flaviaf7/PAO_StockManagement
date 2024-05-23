package services;

import model.Distributor;
import model.Product.Product;
import model.Product.ProductFactory;
import model.Product.ProductInterface;

import java.util.*;

public class ProductService {
    private Set<Product> products;

    public ProductService() {
        this.products = new HashSet<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void addProduct(Scanner scanner) {
        System.out.println("Enter product details:");
        Product newProduct = ProductFactory.createProduct(scanner);
        addProduct(newProduct);
        System.out.println("Product added successfully.");
    }

    public void addProduct(Scanner scanner, Distributor distributor) {
        if (distributor != null) {
            Product product = ProductFactory.createProduct(scanner);
            distributor.addProduct(product);
            this.products.add(product);

            System.out.println("Product added successfully.");
        } else {
            System.out.println("Distributor is null. Unable to add product.");
        }
    }

    public void updateProduct(Scanner scanner){
        System.out.print("Enter product ID to update: ");
        int productIdToUpdate = scanner.nextInt();
        Product productToUpdate = getProductById(productIdToUpdate);
        if (productToUpdate != null) {
            System.out.println("Current Product Details: " + productToUpdate);
            System.out.print("Enter new price: ");
            double newPrice = scanner.nextDouble();
            productToUpdate.setPrice(newPrice);
            System.out.println("Product price updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public void removeProduct(Scanner scanner) {
        System.out.print("Enter product ID to delete: ");
        int productIdToDelete = scanner.nextInt();
        scanner.nextLine();
        Product productToDelete = getProductById(productIdToDelete);
        if (productToDelete != null) {
            removeProduct(productToDelete);
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public Product getProductById(Integer id) {
        for (Product product : this.products) {
            if (product.getProduct_id() == id) {
                return product;
            }
        }
        return null;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void listAllProducts() {
        if (products == null || products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : products) {
                System.out.println(product.toString());
            }
        }
    }

    public void updateProduct(Scanner scanner, Distributor connectedDistributor) {
        System.out.print("Enter product ID to update: ");
        int productIdToUpdate = scanner.nextInt();
        scanner.nextLine();
        Product productToUpdate = getProductById(productIdToUpdate, connectedDistributor);
        if (productToUpdate != null) {
            System.out.println("Current Product Details: " + productToUpdate);
            System.out.print("Enter new price: ");
            double newPrice = scanner.nextDouble();
            productToUpdate.setPrice(newPrice);
            System.out.println("Product price updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void removeProduct(Scanner scanner, Distributor connectedDistributor) {
        System.out.print("Enter product ID to delete: ");
        int productIdToDelete = scanner.nextInt();
        scanner.nextLine();
        Product productToDelete = getProductById(productIdToDelete, connectedDistributor);
        if (productToDelete != null) {
            removeProduct(productToDelete);
            connectedDistributor.getProductsSupplied().remove(productToDelete);
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public Product getProductById(int id, Distributor distributor) {
        return (Product) distributor.getProductsSupplied().stream()
                .filter(product -> product.getProduct_id() == id)
                .findFirst()
                .orElse(null);
    }


    public void listAllProducts(Distributor connectedDistributor) {
        boolean foundProducts = false;
        for (ProductInterface product : connectedDistributor.getProductsSupplied()) {
            System.out.println(product.toString());
            foundProducts = true;
        }
        if (!foundProducts) {
            System.out.println("No products found for the connected distributor.");
        }
    }
}

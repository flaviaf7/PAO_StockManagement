package services;

import database.JdbcConnection;
import model.Distributor;
import model.Product.Product;
import model.Product.ProductFactory;
import model.Product.ProductInterface;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ProductService {
    private Connection connection;

    public ProductService() {
        this.connection = JdbcConnection.getConnection();
    }

    public void addProduct(Scanner scanner, Distributor distributor) {
        if (distributor != null) {
            Product product = ProductFactory.createProduct(scanner);
            distributor.addProduct(product); // Add product to distributor's list
            addProductToDatabase(product); // Add product to the database
            System.out.println("Product added successfully.");
        } else {
            System.out.println("Distributor is null. Unable to add product.");
        }
    }

    private void addProductToDatabase(Product product) {
        String query = "INSERT INTO Product (price, name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, product.getPrice());
            preparedStatement.setString(2, product.getName());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProduct_id(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateProduct(Scanner scanner) {
        System.out.print("Enter product ID to update: ");
        int productIdToUpdate = scanner.nextInt();
        scanner.nextLine();
        Product productToUpdate = getProductById(productIdToUpdate);
        if (productToUpdate != null) {
            System.out.println("Current Product Details: " + productToUpdate);
            System.out.print("Enter new price: ");
            double newPrice = scanner.nextDouble();
            productToUpdate.setPrice(newPrice);
            updateProductInDatabase(productToUpdate);
        } else {
            System.out.println("Product not found.");
        }
    }

    private void updateProductInDatabase(Product product) {
        String query = "UPDATE Product SET price = ? WHERE product_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, product.getPrice());
            preparedStatement.setInt(2, product.getProduct_id());
            preparedStatement.executeUpdate();
            System.out.println("Product price updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(Product product) {
        String query = "DELETE FROM Product WHERE product_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, product.getProduct_id());
            preparedStatement.executeUpdate();
            System.out.println("Product deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(Scanner scanner) {
        System.out.print("Enter product ID to delete: ");
        int productIdToDelete = scanner.nextInt();
        scanner.nextLine();
        Product productToDelete = getProductById(productIdToDelete);
        if (productToDelete != null) {
            removeProduct(productToDelete);
        } else {
            System.out.println("Product not found.");
        }
    }

    public Product getProductById(Integer id) {
        String query = "SELECT * FROM Product WHERE product_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product();
                    product.setProduct_id(resultSet.getInt("product_id"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setName(resultSet.getString("name"));
                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void listAllProducts() {
        String query = "SELECT * FROM Product";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Product product = new Product();
                product.setProduct_id(resultSet.getInt("product_id"));
                product.setPrice(resultSet.getDouble("price"));
                product.setName(resultSet.getString("name"));
                System.out.println(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            updateProductInDatabase(productToUpdate);
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
        } else {
            System.out.println("Product not found.");
        }
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

    public Product getProductById(int id, Distributor distributor) {
        return (Product) distributor.getProductsSupplied().stream()
                .filter(product -> product.getProduct_id() == id)
                .findFirst()
                .orElse(null);
    }


    public HashSet<ProductInterface> getAllProducts() {
        HashSet<ProductInterface> products = new HashSet<>();
        String query = "SELECT * FROM Product";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                double price = resultSet.getDouble("price");
                String name = resultSet.getString("name");

                // Determine product type based on name or other criteria
                // Here, you'll need to implement logic to determine the product type and create instances accordingly
                ProductInterface product = ProductFactory.createProductItem(name, price);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void addProduct(ProductInterface product) {
        String query = "INSERT INTO Product (price, name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, product.getPrice());
            preparedStatement.setString(2, product.getName());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProduct_id(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProductToDistributor(ProductInterface product, Distributor distributor) {
        // Insert the product into the Distributor_Product table
        String query = "INSERT INTO Distributor_Product (distributorID, productID) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, distributor.getID());
            preparedStatement.setInt(2, product.getProduct_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

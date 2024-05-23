package services;

import exception.DistributorNotFoundException;
import model.Address;
import model.Distributor;
import model.Product.Product;
import model.Product.ProductInterface;
import database.JdbcConnection;

import java.sql.*;
import java.util.*;

public class DistributorService {
    private final Connection connection;

    public DistributorService() {
        this.connection = JdbcConnection.getConnection();
    }

    public void addDistributor(Distributor distributor) {
        String query = "INSERT INTO Distributor (name, emailAddress, address_id, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, distributor.getName());
            preparedStatement.setString(2, distributor.getEmailAddress());
            preparedStatement.setInt(3, distributor.getAddress().getID());
            preparedStatement.setString(4, distributor.getPassword());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    distributor.setID(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listAllDistributors() {
        String query = "SELECT d.ID, d.name, d.emailAddress, d.password, a.street, a.city, a.county, a.postalCode, a.country FROM Distributor d JOIN Address a ON d.address_id = a.ID";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int distributorId = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                String emailAddress = resultSet.getString("emailAddress");
                String password = resultSet.getString("password");
                String street = resultSet.getString("street");
                String city = resultSet.getString("city");
                String county = resultSet.getString("county");
                String postalCode = resultSet.getString("postalCode");
                String country = resultSet.getString("country");

                Address address = new Address(street, city, county, postalCode, country);

                Distributor distributor = new Distributor(distributorId, name, emailAddress, address, password);
                System.out.println(distributor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAllProductsSupplied(Integer id) {
        try {
            Distributor distributor = getDistributorById(id);
            if (distributor != null) {
                Set<ProductInterface> productsSupplied = getProductsFromDistributor(id);
                if (!productsSupplied.isEmpty()) {
                    for (ProductInterface product : productsSupplied) {
                        System.out.println(product.toString());
                    }
                } else {
                    System.out.println("No products supplied by this distributor.");
                }
            } else {
                System.out.println("Distributor not found.");
            }
        } catch (DistributorNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public Distributor getDistributorById(Integer id) throws DistributorNotFoundException {
        String query = "SELECT d.name, d.emailAddress, d.password, a.street, a.city, a.county, a.postalCode, a.country FROM Distributor d JOIN Address a ON d.address_id = a.ID WHERE d.ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String emailAddress = resultSet.getString("emailAddress");
                    String password = resultSet.getString("password");
                    String street = resultSet.getString("street");
                    String city = resultSet.getString("city");
                    String county = resultSet.getString("county");
                    String postalCode = resultSet.getString("postalCode");
                    String country = resultSet.getString("country");

                    Address address = new Address(street, city, county, postalCode, country);

                    return new Distributor(id, name, emailAddress, address, password);
                } else {
                    throw new DistributorNotFoundException("Distributor with ID " + id + " not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<Distributor> getDistributors() {
        Set<Distributor> distributors = new HashSet<>();
        String query = "SELECT d.ID, d.name, d.emailAddress, d.password, a.street, a.city, a.county, a.postalCode, a.country FROM Distributor d JOIN Address a ON d.address_id = a.ID";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int distributorId = resultSet.getInt("ID");
                String name = resultSet.getString("name");
                String emailAddress = resultSet.getString("emailAddress");
                String password = resultSet.getString("password");
                String street = resultSet.getString("street");
                String city = resultSet.getString("city");
                String county = resultSet.getString("county");
                String postalCode = resultSet.getString("postalCode");
                String country = resultSet.getString("country");

                Address address = new Address(street, city, county, postalCode, country);

                Distributor distributor = new Distributor(distributorId, name, emailAddress, address, password);
                distributors.add(distributor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return distributors;
    }

    public Set<ProductInterface> getProductsFromDistributor(Integer distributorId) {
        Set<ProductInterface> products = new HashSet<>();
        String query = "SELECT product_id, name, price FROM Product WHERE distributor_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, distributorId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int productId = resultSet.getInt("product_id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");

                    // Create the product instance
                    ProductInterface product = new Product(productId, name, price);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }


    public ProductInterface getProductFromDistributor(Integer distributorId, Integer productId) {
        try {
            Distributor distributor = getDistributorById(distributorId);
            List<ProductInterface> products = distributor.getProductsSupplied();
            for (ProductInterface product : products) {
                if (product.getProduct_id() == productId) {
                    return product;
                }
            }
        } catch (DistributorNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void addAddress(Address address) {
        String query = "INSERT INTO Address (street, city, county, postalCode, country) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, address.getStreet());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getCounty());
            preparedStatement.setString(4, address.getPostalCode());
            preparedStatement.setString(5, address.getCountry());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    address.setID(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Address> getAllAddresses() {
        Set<Address> addresses = new HashSet<>();
        String query = "SELECT * FROM Address";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String street = resultSet.getString("street");
                String city = resultSet.getString("city");
                String county = resultSet.getString("county");
                String postalCode = resultSet.getString("postalCode");
                String country = resultSet.getString("country");

                Address address = new Address(id, street, city, county, postalCode, country);
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses;
    }

}

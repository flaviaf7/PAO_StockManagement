package services;
import exception.DistributorNotFoundException;
import model.Distributor;
import model.Product.ProductInterface;

import java.util.*;

public class DistributorService {
    private Set<Distributor> distributors;
    public DistributorService(List<Distributor> distributors){
        this.distributors = new HashSet<>();
        this.distributors.addAll(distributors);
    }

    public DistributorService(){
        this.distributors = new HashSet<>();
    }

    public void addDistributor(Distributor distributor){
        this.distributors.add(distributor);
    }

    public void listAllDistributors(){
        if (distributors == null || distributors.isEmpty()){
            System.out.println("No distributors found.");
        }
        else {
            for (Distributor distributor : distributors) {
                System.out.println(distributor.toString());
            }
        }

    }
    public Distributor getDistributorById(Integer id) throws DistributorNotFoundException{
        for (Distributor distributor : distributors){
            if (distributor.getID().equals(id)){
                return distributor;
            }
        }
        throw new DistributorNotFoundException("Distributor with ID " + id + " not found.");
    }

    public Set<Distributor> getDistributors(){
        return this.distributors;
    }

    public void showAllProductsSupplied(Integer id){
        try {
            Distributor distributor = getDistributorById(id);
            List<ProductInterface> products = distributor.getProductsSupplied();
            for (ProductInterface product : products) {
                System.out.println(product.toString());
            }
        } catch (DistributorNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public ProductInterface getProductFromDistributor(Integer distributorId, Integer productId){
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
}

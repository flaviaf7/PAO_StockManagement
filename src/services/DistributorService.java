package services;
import model.Distributor;
import model.Product.Product;
import model.Product.ProductInterface;

import java.util.*;

public class DistributorService {
    private Set<Distributor> distributors;
    public DistributorService(List<Distributor> distributors){
        this.distributors = new HashSet<Distributor>();
        for (Distributor distributor : distributors){
            this.distributors.add(distributor);
        }
    }

    public DistributorService(){
        this.distributors = new HashSet<Distributor>();
    }

    public void addDistributor(Distributor distributor){
        this.distributors.add(distributor);
    }

    public void removeDistributor(Distributor distributor){
        this.distributors.remove(distributor);
    }

    public void listAllDistributors(){
        for (Distributor distributor : distributors){
            System.out.println(distributor.toString());
        }
    }
    public Distributor getDistributorById(Integer id){
        for (Distributor distributor : distributors){
            if (distributor.getID().equals(id)){
                return distributor;
            }
        }
        return null;
    }

    public Set<Distributor> getDistributors(){
        return this.distributors;
    }

    public void showAllProductsSupplied(Integer id){
        Distributor distributor = getDistributorById(id);
        List<ProductInterface> products = distributor.getProductsSupplied();
        for (ProductInterface product : products){
            System.out.println(product.toString());
        }
    }

    public ProductInterface getProductFromDistributor(Integer distributorId, Integer productId){
        Distributor distributor = getDistributorById(distributorId);
        List<ProductInterface> products = distributor.getProductsSupplied();
        for (ProductInterface product : products){
            if (product.getProduct_id() == productId){
                return product;
            }
        }
        return null;
    }
}

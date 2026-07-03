package com.ecommerce.service;

import com.ecommerce.model.Product;
import com.ecommerce.model.enums.Region;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.util.IdGenerator;

import java.util.List;

public class CatalogService {
    private List<Product> catalog;
    private ProductRepository repository;

    public CatalogService() {
        this.repository = new ProductRepository();
        this.catalog = repository.loadAll();
    }

    public void addProduct(Product product) {
        catalog.add(product);
        repository.saveAll(catalog);
    }

    public void printSimpleCatalog() {
        System.out.println("===============================================================");
        System.out.println("                   AVAILABLE PRODUCTS                          ");
        System.out.println("===============================================================");
        for (Product p : catalog) {
            if (p.checkAvailability()) {
                System.out.printf("ID: %s | Name: %-30s | Base Price: $%.2f\n", p.getId(), p.getName(), p.getBasePrice());
            }
        }
        System.out.println("===============================================================");
    }


    public List<Product> getAllProducts() {
        return catalog;
    }

    public String generateNewId(String type) {
        return IdGenerator.generateProductId(type, catalog);
    }

    public Product getProductById(String id) {
        for (Product p : catalog) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Lists the catalog uniformly regardless of product type.
     * Demonstrates Polymorphism - calls overridden methods without instanceof.
     */
    public void printCatalog(Region region, String place) {
        System.out.println("===============================================================");
        System.out.println("                   E-COMMERCE CATALOG                          ");
        System.out.println("===============================================================");
        System.out.println("Shipping Region: " + region.name() + " | Place: " + place);
        System.out.println("---------------------------------------------------------------");

        if (catalog.isEmpty()) {
            System.out.println("Catalog is currently empty.");
            System.out.println("---------------------------------------------------------------");
            return;
        }

        for (Product product : catalog) {
            System.out.println("ID: " + product.getId() + " | Name: " + product.getName());
            
            if (product.checkAvailability()) {
                System.out.println("Status: Available");
                System.out.printf("Base Price: $%.2f\n", product.getBasePrice());
                System.out.printf("Shipping:   $%.2f\n", product.calculateShippingCost(region, place));
                System.out.printf("Tax:        $%.2f\n", product.calculateTax(region, place));
                System.out.printf("TOTAL:      $%.2f\n", product.calculateTotalCost(region, place));
                System.out.println("Delivery: " + product.getDeliverabilityDetails());
            } else {
                System.out.println("Status: OUT OF STOCK / UNAVAILABLE");
                System.out.printf("Base Price: $%.2f\n", product.getBasePrice());
            }
            System.out.println("---------------------------------------------------------------");
        }
    }
}

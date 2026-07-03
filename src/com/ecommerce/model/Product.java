package com.ecommerce.model;

import com.ecommerce.model.enums.Region;
import java.util.Objects;

/**
 * Abstract base class for all products in the catalog.
 * Demonstrates Abstraction and serves as the foundation for Polymorphism.
 */
public abstract class Product {
    private String id;
    private String name;
    private double basePrice;

    public Product(String id, String name, double basePrice) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    // --- Abstract Hooks for Polymorphic Behavior ---

    /**
     * Calculates shipping cost which varies by product type and place.
     */
    public abstract double calculateShippingCost(Region region, String place);

    /**
     * Calculates tax which varies by product type and region/place.
     */
    public abstract double calculateTax(Region region, String place);

    /**
     * Checks if the product is currently available to be sold.
     */
    public abstract boolean checkAvailability();

    /**
     * Returns details about how this product is delivered.
     */
    public abstract String getDeliverabilityDetails();

    /**
     * Returns CSV string representation of the specific product.
     */
    public abstract String toCsv();

    // -----------------------------------------------

    /**
     * Calculates the final total price (base + shipping + tax).
     * Template Method pattern.
     */
    public double calculateTotalCost(Region region, String place) {
        return getBasePrice() + calculateShippingCost(region, place) + calculateTax(region, place);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - $%.2f", id, name, basePrice);
    }
}

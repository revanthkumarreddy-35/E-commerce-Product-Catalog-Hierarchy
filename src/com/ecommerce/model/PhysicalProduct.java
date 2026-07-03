package com.ecommerce.model;

import com.ecommerce.model.enums.Region;

public class PhysicalProduct extends Product {
    private double weightInKg;
    private int stockQuantity;

    private static final double BASE_SHIPPING_RATE = 5.0; // $5 per kg

    public PhysicalProduct(String id, String name, double basePrice, double weightInKg, int stockQuantity) {
        super(id, name, basePrice);
        this.weightInKg = weightInKg;
        this.stockQuantity = stockQuantity;
    }

    public double getWeightInKg() { return weightInKg; }
    public void setWeightInKg(double weightInKg) { this.weightInKg = weightInKg; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    @Override
    public double calculateShippingCost(Region region, String place) {
        // Shipping based on weight and region multiplier
        double cost = weightInKg * BASE_SHIPPING_RATE * region.getBaseShippingMultiplier();
        
        // Dynamic place-based fees
        if (region == Region.INTERNATIONAL) {
            // Simulate variable customs/distance fees based on place name length
            cost += place.length() * 2.0; 
        } else {
            // Domestic place variance
            if (place.toLowerCase().contains("island") || place.toLowerCase().contains("hawaii")) {
                cost += 15.0; // Remote fee
            } else {
                cost += place.length() * 0.5; // Small distance variance
            }
        }
        return cost;
    }

    @Override
    public double calculateTax(Region region, String place) {
        // Physical goods typically have a standard sales tax
        double taxRate = (region == Region.INTERNATIONAL) ? 0.15 : 0.08;
        
        // Specific place taxes (e.g. California high tax)
        if (place.toLowerCase().contains("california") || place.toLowerCase().contains("ca")) {
            taxRate += 0.02; 
        } else if (place.toLowerCase().contains("europe")) {
            taxRate += 0.05;
        }

        return getBasePrice() * taxRate;
    }

    @Override
    public boolean checkAvailability() {
        return stockQuantity > 0;
    }

    @Override
    public String getDeliverabilityDetails() {
        return String.format("Ships via ground/air. Weight: %.2f kg", weightInKg);
    }

    @Override
    public String toCsv() {
        return String.format("PHYSICAL,%s,%s,%.2f,%.2f,%d", 
            getId(), getName(), getBasePrice(), weightInKg, stockQuantity);
    }
}

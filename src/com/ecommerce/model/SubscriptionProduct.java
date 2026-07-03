package com.ecommerce.model;

import com.ecommerce.model.enums.Region;

public class SubscriptionProduct extends Product {
    private String billingPeriod; // e.g., "Monthly", "Yearly"
    private boolean isActive;

    public SubscriptionProduct(String id, String name, double basePrice, String billingPeriod, boolean isActive) {
        super(id, name, basePrice);
        this.billingPeriod = billingPeriod;
        this.isActive = isActive;
    }

    public String getBillingPeriod() { return billingPeriod; }
    public void setBillingPeriod(String billingPeriod) { this.billingPeriod = billingPeriod; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public double calculateShippingCost(Region region, String place) {
        // Subscriptions don't have shipping costs.
        return 0.0;
    }

    @Override
    public double calculateTax(Region region, String place) {
        // Subscriptions might have specific service taxes depending on place.
        double taxRate = 0.10;
        if (place.toLowerCase().contains("texas")) {
            taxRate = 0.08; // Example state variance
        }
        return getBasePrice() * taxRate; 
    }

    @Override
    public boolean checkAvailability() {
        return isActive;
    }

    @Override
    public String getDeliverabilityDetails() {
        return String.format("Recurring %s Subscription. Access granted upon payment.", billingPeriod);
    }

    @Override
    public String toCsv() {
        return String.format("SUBSCRIPTION,%s,%s,%.2f,%s,%b", 
            getId(), getName(), getBasePrice(), billingPeriod, isActive);
    }
}

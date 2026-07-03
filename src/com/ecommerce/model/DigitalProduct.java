package com.ecommerce.model;

import com.ecommerce.model.enums.Region;

public class DigitalProduct extends Product {
    private String downloadLink;
    private double fileSizeMB;
    private boolean isServerOnline;

    public DigitalProduct(String id, String name, double basePrice, String downloadLink, double fileSizeMB, boolean isServerOnline) {
        super(id, name, basePrice);
        this.downloadLink = downloadLink;
        this.fileSizeMB = fileSizeMB;
        this.isServerOnline = isServerOnline;
    }

    public String getDownloadLink() { return downloadLink; }
    public void setDownloadLink(String downloadLink) { this.downloadLink = downloadLink; }

    public double getFileSizeMB() { return fileSizeMB; }
    public void setFileSizeMB(double fileSizeMB) { this.fileSizeMB = fileSizeMB; }

    public boolean isServerOnline() { return isServerOnline; }
    public void setServerOnline(boolean serverOnline) { this.isServerOnline = serverOnline; }

    @Override
    public double calculateShippingCost(Region region, String place) {
        // Shipping is meaningless for digital products, always 0 regardless of place.
        return 0.0;
    }

    @Override
    public double calculateTax(Region region, String place) {
        // Digital goods might have lower tax or different digital service taxes.
        double taxRate = 0.05; // Standard
        
        if (region == Region.INTERNATIONAL && place.toLowerCase().contains("eu")) {
             taxRate = 0.20; // European Digital Services Tax
        }
        return getBasePrice() * taxRate;
    }

    @Override
    public boolean checkAvailability() {
        return isServerOnline;
    }

    @Override
    public String getDeliverabilityDetails() {
        return String.format("Instant Digital Download (%.1f MB). Link: %s", fileSizeMB, downloadLink);
    }

    @Override
    public String toCsv() {
        return String.format("DIGITAL,%s,%s,%.2f,%s,%.2f,%b", 
            getId(), getName(), getBasePrice(), downloadLink, fileSizeMB, isServerOnline);
    }
}

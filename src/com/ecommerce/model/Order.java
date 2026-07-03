package com.ecommerce.model;

import com.ecommerce.model.enums.Region;

public class Order {
    private String trackingId;
    private String username;
    private String productId;
    private Region region;
    private String destinationPlace;
    private double totalCost;
    private String status;

    public Order(String trackingId, String username, String productId, Region region, String destinationPlace, double totalCost, String status) {
        this.trackingId = trackingId;
        this.username = username;
        this.productId = productId;
        this.region = region;
        this.destinationPlace = destinationPlace;
        this.totalCost = totalCost;
        this.status = status;
    }

    public String getTrackingId() { return trackingId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getProductId() { return productId; }
    public Region getRegion() { return region; }
    public String getDestinationPlace() { return destinationPlace; }
    public double getTotalCost() { return totalCost; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toCsv() {
        return String.format("%s,%s,%s,%s,%s,%.2f,%s", 
            trackingId, username, productId, region.name(), destinationPlace, totalCost, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "trackingId='" + trackingId + '\'' +
                ", username='" + username + '\'' +
                ", productId='" + productId + '\'' +
                ", region=" + region +
                ", destinationPlace='" + destinationPlace + '\'' +
                ", totalCost=" + totalCost +
                ", status='" + status + '\'' +
                '}';
    }
}

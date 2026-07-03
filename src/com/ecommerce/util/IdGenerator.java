package com.ecommerce.util;

import com.ecommerce.model.Product;
import com.ecommerce.model.Order;

import java.util.List;

public class IdGenerator {

    public static String generateProductId(String type, List<Product> existingProducts) {
        String prefix = type.substring(0, 1).toUpperCase() + "-"; // 'P', 'D', 'S'
        int maxId = 0;
        
        for (Product product : existingProducts) {
            if (product.getId().startsWith(prefix)) {
                try {
                    int num = Integer.parseInt(product.getId().substring(2));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        
        return String.format("%s%03d", prefix, maxId + 1);
    }

    public static String generateTrackingId(List<Order> existingOrders) {
        String prefix = "TRK-";
        int maxId = 0;
        
        for (Order order : existingOrders) {
            if (order.getTrackingId().startsWith(prefix)) {
                try {
                    int num = Integer.parseInt(order.getTrackingId().substring(4));
                    if (num > maxId) {
                        maxId = num;
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        
        return String.format("%s%03d", prefix, maxId + 1);
    }
}

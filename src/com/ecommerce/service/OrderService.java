package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.model.enums.Region;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Order> orders;
    private OrderRepository repository;

    public OrderService() {
        this.repository = new OrderRepository();
        this.orders = repository.loadAll();
    }

    public Order createOrder(User user, Product product, Region region, String place) {
        if (!product.checkAvailability()) {
            System.out.println("Sorry, this product is currently unavailable.");
            return null;
        }

        String trackingId = IdGenerator.generateTrackingId(orders);
        double totalCost = product.calculateTotalCost(region, place);
        
        Order newOrder = new Order(trackingId, user.getUsername(), product.getId(), region, place, totalCost, "Processing");
        orders.add(newOrder);
        repository.saveAll(orders);
        
        return newOrder;
    }

    public Order trackOrder(String trackingId, User user) {
        for (Order order : orders) {
            if (order.getTrackingId().equalsIgnoreCase(trackingId) && 
                order.getUsername().equalsIgnoreCase(user.getUsername())) {
                return order;
            }
        }
        return null;
    }

    public List<Order> getOrdersForUser(User user) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUsername().equalsIgnoreCase(user.getUsername())) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }
}

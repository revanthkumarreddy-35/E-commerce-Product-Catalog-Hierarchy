package com.ecommerce.repository;

import com.ecommerce.model.Order;
import com.ecommerce.model.enums.Region;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private static final String FILE_PATH = "data/orders.csv";

    public OrderRepository() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    public List<Order> loadAll() {
        List<Order> orders = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) return orders;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                
                try {
                    Order order = new Order(
                        parts[0], // trackingId
                        parts[1], // username
                        parts[2], // productId
                        Region.valueOf(parts[3]), 
                        parts[4], // destinationPlace
                        Double.parseDouble(parts[5]), 
                        parts[6]  // status
                    );
                    orders.add(order);
                } catch (Exception e) {
                    System.err.println("Error parsing order line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading orders: " + e.getMessage());
        }
        return orders;
    }

    public void saveAll(List<Order> orders) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Order order : orders) {
                bw.write(order.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving orders: " + e.getMessage());
        }
    }
}

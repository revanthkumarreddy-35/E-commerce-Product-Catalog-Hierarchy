package com.ecommerce.repository;

import com.ecommerce.model.DigitalProduct;
import com.ecommerce.model.PhysicalProduct;
import com.ecommerce.model.Product;
import com.ecommerce.model.SubscriptionProduct;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static final String FILE_PATH = "data/products.csv";

    public ProductRepository() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    public List<Product> loadAll() {
        List<Product> products = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) return products;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                String type = parts[0];

                try {
                    if (type.equals("PHYSICAL")) {
                        products.add(new PhysicalProduct(parts[1], parts[2], Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), Integer.parseInt(parts[5])));
                    } else if (type.equals("DIGITAL")) {
                        products.add(new DigitalProduct(parts[1], parts[2], Double.parseDouble(parts[3]), parts[4], Double.parseDouble(parts[5]), Boolean.parseBoolean(parts[6])));
                    } else if (type.equals("SUBSCRIPTION")) {
                        products.add(new SubscriptionProduct(parts[1], parts[2], Double.parseDouble(parts[3]), parts[4], Boolean.parseBoolean(parts[5])));
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing product line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading products: " + e.getMessage());
        }
        return products;
    }

    public void saveAll(List<Product> products) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Product product : products) {
                bw.write(product.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving products: " + e.getMessage());
        }
    }
}

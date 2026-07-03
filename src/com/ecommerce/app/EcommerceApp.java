package com.ecommerce.app;

import com.ecommerce.model.DigitalProduct;
import com.ecommerce.model.Order;
import com.ecommerce.model.PhysicalProduct;
import com.ecommerce.model.Product;
import com.ecommerce.model.SubscriptionProduct;
import com.ecommerce.model.User;
import com.ecommerce.model.enums.Region;
import com.ecommerce.model.enums.Role;
import com.ecommerce.service.AuthService;
import com.ecommerce.service.CatalogService;
import com.ecommerce.service.OrderService;

import java.util.Scanner;

public class EcommerceApp {

    private static AuthService authService = new AuthService();
    private static CatalogService catalogService = new CatalogService();
    private static OrderService orderService = new OrderService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Welcome to E-Commerce System ===");
            System.out.println("1. Login");
            System.out.println("2. Register as Customer");
            System.out.println("3. Exit");
            System.out.print("Select option: ");

            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    loginFlow();
                    break;
                case "2":
                    registerFlow();
                    break;
                case "3":
                    System.out.println("Exiting System. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void loginFlow() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = authService.login(username, password);

        if (user != null) {
            System.out.println("\nLogin Successful! Welcome, " + user.getUsername() + ".");
            if (user.getRole() == Role.ADMIN) {
                adminMenu(user);
            } else {
                customerMenu(user);
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void registerFlow() {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine();
        System.out.print("Choose a password: ");
        String password = scanner.nextLine();

        boolean success = authService.registerCustomer(username, password);
        if (success) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Registration failed. Username might already be taken.");
        }
    }

    private static void adminMenu(User admin) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Admin Panel (" + admin.getUsername() + ") ---");
            System.out.println("1. View Domestic Catalog");
            System.out.println("2. View International Catalog");
            System.out.println("3. Add New Product");
            System.out.println("4. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            System.out.println();
            
            switch (choice) {
                case "1":
                    System.out.print("Enter specific Domestic Place: ");
                    String domPlace = scanner.nextLine();
                    catalogService.printCatalog(Region.DOMESTIC, domPlace);
                    break;
                case "2":
                    System.out.print("Enter specific International Place: ");
                    String intPlace = scanner.nextLine();
                    catalogService.printCatalog(Region.INTERNATIONAL, intPlace);
                    break;
                case "3":
                    addProductFlow();
                    break;
                case "4":
                    back = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void customerMenu(User customer) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Customer Portal (" + customer.getUsername() + ") ---");
            System.out.println("1. Simulate Purchase");
            System.out.println("2. Track Specific Order");
            System.out.println("3. View All My Orders");
            System.out.println("4. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();
            System.out.println();
            
            switch (choice) {
                case "1":
                    purchaseFlow(customer);
                    break;
                case "2":
                    System.out.print("Enter your Tracking ID (e.g., TRK-001): ");
                    String trackingId = scanner.nextLine();
                    Order order = orderService.trackOrder(trackingId, customer);
                    if (order != null) {
                        System.out.println("Order Found: " + order.toString());
                    } else {
                        System.out.println("No order found with Tracking ID '" + trackingId + "' for your account.");
                    }
                    break;
                case "3":
                    java.util.List<Order> myOrders = orderService.getOrdersForUser(customer);
                    if (myOrders.isEmpty()) {
                        System.out.println("You have no orders yet.");
                    } else {
                        System.out.println("--- My Orders ---");
                        for (Order o : myOrders) {
                            System.out.println(o.toString());
                        }
                    }
                    break;
                case "4":
                    back = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addProductFlow() {
        System.out.println("Select Product Type to Add:");
        System.out.println("1. Physical");
        System.out.println("2. Digital");
        System.out.println("3. Subscription");
        System.out.print("Choice: ");
        String typeChoice = scanner.nextLine();

        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Base Price: $");
        double price = Double.parseDouble(scanner.nextLine());

        Product newProduct = null;

        if (typeChoice.equals("1")) {
            String id = catalogService.generateNewId("Physical");
            System.out.print("Enter Weight (kg): ");
            double weight = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Stock Quantity: ");
            int stock = Integer.parseInt(scanner.nextLine());
            newProduct = new PhysicalProduct(id, name, price, weight, stock);
            System.out.println("Generated ID: " + id);
            
        } else if (typeChoice.equals("2")) {
            String id = catalogService.generateNewId("Digital");
            System.out.print("Enter Download Link: ");
            String link = scanner.nextLine();
            System.out.print("Enter File Size (MB): ");
            double size = Double.parseDouble(scanner.nextLine());
            System.out.print("Is Server Online? (true/false): ");
            boolean online = Boolean.parseBoolean(scanner.nextLine());
            newProduct = new DigitalProduct(id, name, price, link, size, online);
            System.out.println("Generated ID: " + id);

        } else if (typeChoice.equals("3")) {
            String id = catalogService.generateNewId("Subscription");
            System.out.print("Enter Billing Period (e.g., Monthly, Yearly): ");
            String period = scanner.nextLine();
            System.out.print("Is Active? (true/false): ");
            boolean active = Boolean.parseBoolean(scanner.nextLine());
            newProduct = new SubscriptionProduct(id, name, price, period, active);
            System.out.println("Generated ID: " + id);
        } else {
            System.out.println("Invalid product type.");
            return;
        }

        if (newProduct != null) {
            catalogService.addProduct(newProduct);
            System.out.println("Product successfully added and saved to CSV!");
        }
    }

    private static void purchaseFlow(User customer) {
        catalogService.printSimpleCatalog();
        
        System.out.print("\nEnter the Product ID you wish to purchase: ");
        String pId = scanner.nextLine();
        Product product = catalogService.getProductById(pId);
        
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Is this Domestic (1) or International (2)? ");
        String regChoice = scanner.nextLine();
        Region region = regChoice.equals("2") ? Region.INTERNATIONAL : Region.DOMESTIC;

        System.out.print("Enter your destination place: ");
        String place = scanner.nextLine();

        Order newOrder = orderService.createOrder(customer, product, region, place);
        
        if (newOrder != null) {
            System.out.println("\n--- Order Successfully Placed for " + customer.getUsername() + "! ---");
            System.out.println("Your Tracking ID is: " + newOrder.getTrackingId());
            System.out.printf("Total Charged: $%.2f\n", newOrder.getTotalCost());
            System.out.println("----------------------------------");
        }
    }
}

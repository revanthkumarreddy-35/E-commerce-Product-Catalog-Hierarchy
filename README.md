# 🛒 E-Commerce Product Catalog

## Capstone Project — Java Full Stack (PS-29, Module C)

A console-based E-commerce Product Catalog built in Java that focuses on Object-Oriented Programming (OOP) concepts such as **Inheritance**, **Abstraction**, and **Polymorphism**. The project strictly avoids `instanceof` checks by leveraging a robust polymorphic architecture for calculating deliverability and taxes.

---

## 🏗️ Architecture

```
com.ecommerce/
├── model/           → Domain entities (Product, PhysicalProduct, DigitalProduct, SubscriptionProduct)
│   └── enums/       → Enums (Region)
├── service/         → Business logic layer (CatalogService)
└── app/             → Entry point & console UI (EcommerceApp)
```

**Layered architecture** with clean separation of concerns and robust polymorphic behavior.

---

## ✨ Features

### Core Use Cases
1. **Abstract Product Hierarchy** — A base `Product` class with shared attributes and abstract hooks for logic.
2. **Specialized Product Types** — Handling specific logic for Physical, Digital, and Subscription products.
3. **Polymorphic Calculations** — Automatic, type-safe computation of shipping and tax rates dependent on product type and region.
4. **Uniform Catalog Listing** — Displaying diverse products in a unified view without casting or type checks.

### Advanced Concepts Demonstrated
- **Inheritance & Abstraction** — Defining `calculateShippingCost()`, `calculateTax()`, and `checkAvailability()` in the base class and enforcing implementation in subclasses.
- **Polymorphism** — Iterating through `List<Product>` and calling overridden methods, ensuring subtype-specific logic executes dynamically.
- **Template Method Pattern** — The base `Product` class provides `calculateTotalCost()` which relies on abstract methods implemented by subclasses.

---

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher
- Terminal or Command Prompt

### Compile & Run
```bash
# Navigate to the project directory
cd EcommerceProductCatalog

# Compile all Java files
javac -d out src/com/ecommerce/model/enums/*.java src/com/ecommerce/model/*.java src/com/ecommerce/service/*.java src/com/ecommerce/app/*.java

# Run the application
java -cp out com.ecommerce.app.EcommerceApp
```

### Sample Data
The project initializes with:
- **Physical Products** (e.g., Wireless Headphones, Office Chair - out of stock)
- **Digital Products** (e.g., E-Book, Game Key - server offline)
- **Subscription Products** (e.g., Streaming Service, Cloud Storage)

---

## 💰 Tax & Shipping Rules

| Product Type | Shipping Rule | Tax Rule |
|---|---|---|
| **Physical** | Varies by weight and region multiplier | 8% Domestic, 15% International |
| **Digital** | $0 (N/A) | 5% Standard, 20% Europe (Digital Services Tax) |
| **Subscription** | $0 (N/A) | Flat 10% Service Tax |

Region Multipliers for Physical Shipping:
- DOMESTIC: 1.0x
- INTERNATIONAL: 2.5x
- EUROPE: 2.0x
- ASIA: 1.8x

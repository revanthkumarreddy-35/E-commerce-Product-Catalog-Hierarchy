# 🛒 E-Commerce Product Catalog

## Capstone Project — Java Full Stack (PS-29, Module C)

A highly advanced, console-based E-commerce Product Catalog built in Java. This project focuses heavily on **Object-Oriented Programming (OOP)**, **File Persistence**, and **Security**. It strictly avoids `instanceof` anti-patterns by leveraging a robust polymorphic architecture for calculating deliverability and taxes, while also providing full user authentication and order tracking.

---

## 🏗️ Architecture

```
com.ecommerce/
├── model/           → Domain entities (User, Order, Product, PhysicalProduct...)
│   └── enums/       → Enums (Region, Role)
├── repository/      → Data persistence layer handling File I/O (CSV parsing)
├── service/         → Business logic layer (AuthService, CatalogService, OrderService)
├── util/            → Utilities (IdGenerator)
└── app/             → Entry point & console UI (EcommerceApp)
```

**Layered architecture** with clean separation of concerns, strict encapsulation, and robust polymorphic behavior.

---

## ✨ Key Features

### 1. Authentication & Role-Based Access Control (RBAC)
- **Login & Registration:** Users must authenticate before accessing the system.
- **Admin Portal:** Admins can view customized catalogs and add new products to the inventory.
- **Customer Portal:** Customers can view the shop, simulate purchases, and track their specific order history.
- **Data Privacy:** Customers can only view and track orders that are tied to their specific account.

### 2. Polymorphic CSV Data Persistence
- **File I/O:** All data is permanently saved across sessions in `data/users.csv`, `data/products.csv`, and `data/orders.csv`.
- **Factory Parsing:** The `ProductRepository` reads mixed product types from a single file by parsing a `Type` column, successfully resurrecting the correct subclass (`Physical`, `Digital`, or `Subscription`) without breaking polymorphism.

### 3. Dynamic Business Logic
- **"Place" Modifiers:** Cost calculations aren't just static numbers. They react dynamically to the specific destination entered by the user.
  - *International Rates:* Calculated using string-length algorithms to simulate real-time distance and customs fees.
  - *Remote Locations:* Keywords like "Hawaii" trigger specific remote-delivery surcharges for physical goods.
- **Auto-ID Generation:** The system automatically parses existing CSV files on startup to safely generate the next sequential ID (`P-001`, `TRK-001`), mimicking SQL sequence behavior.

### 4. Advanced OOP Concepts Demonstrated
- **Template Method Pattern** — The base `Product` class provides `calculateTotalCost()` which relies on abstract methods (`calculateShippingCost`, `calculateTax`) implemented by the subclasses.
- **Strict Anti-`instanceof`** — The `CatalogService` can process thousands of mixed products without ever needing to cast objects or check their specific type.

---

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher
- Terminal or Command Prompt

### Compile & Run
```bash
# Navigate to the project directory
cd "EcommerceProductCatalog"

# Compile all Java files
javac -d out src/com/ecommerce/model/enums/*.java src/com/ecommerce/model/*.java src/com/ecommerce/util/*.java src/com/ecommerce/repository/*.java src/com/ecommerce/service/*.java src/com/ecommerce/app/*.java

# Run the application
java -cp out com.ecommerce.app.EcommerceApp
```

### Initial Setup
On the very first run, if no users exist, the system will automatically create a default Admin account:
- **Username:** `admin`
- **Password:** `admin123`

Any new user who registers through the console flow will default to being a `CUSTOMER`.

---

## 💰 Tax & Shipping Rules

| Product Type | Shipping Rule | Tax Rule |
|---|---|---|
| **Physical** | Varies by weight, region multiplier, and specific destination | 8% Domestic, 15% International |
| **Digital** | $0 (N/A) | 5% Standard, 20% Europe (Digital Services Tax) |
| **Subscription** | $0 (N/A) | Flat 10% Service Tax |

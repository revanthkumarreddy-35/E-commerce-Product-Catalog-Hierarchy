package com.ecommerce.service;

import com.ecommerce.model.User;
import com.ecommerce.model.enums.Role;
import com.ecommerce.repository.UserRepository;

import java.util.List;

public class AuthService {
    private List<User> users;
    private UserRepository repository;

    public AuthService() {
        this.repository = new UserRepository();
        this.users = repository.loadAll();
        
        // Initialize default admin if no users exist
        if (users.isEmpty()) {
            users.add(new User("admin", "admin123", Role.ADMIN));
            repository.saveAll(users);
        }
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Login failed
    }

    public boolean registerCustomer(String username, String password) {
        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return false; // Username taken
            }
        }
        
        User newCustomer = new User(username, password, Role.CUSTOMER);
        users.add(newCustomer);
        repository.saveAll(users);
        return true;
    }
}

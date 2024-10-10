package org.example.service;

import org.example.model.User;

import java.util.Map;
import java.util.UUID;

public class UserService {
    private final Map<String, User> users;

    public UserService(Map<String, User> users) {
        this.users = users;
    }

    public User createAdmin(String adminName, String adminEmail, String adminPassword) {
        User admin = new User(adminName, adminEmail,adminPassword);
        users.put(adminEmail, admin);
        return admin;
    }

    public String registerUser(String name, String email, String password) {
        if(users.containsKey(email)) {
            return "Email is already in use.";
        }
        User newUser = new User(name, email, password);
        users.put(email, newUser);
        return "Registration successful.";
    }

    public String loginUser(String email, String password) {
        User user = users.get(email);

        if(user == null) {
            return "User not found.";
        }

        if(!user.getPassword().equals(password)) {
            return "Wrong password.";
        }

        return "Authorization successful.";
    }

    public String editUser(String email, String newName, String newEmail, String newPassword) {
        User user = users.get(email);
        if(user == null) {
            return "User not found.";
        }

        if(users.containsKey(newEmail)) {
            return "Email is already in use.";
        }

        user.setName(newName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);

        if(!email.equals(newEmail)) {
            users.remove(email);
            users.put(newEmail, user);
        }
        return "Data updated.";
    }

    public String deleteUser(String email) {
        if(users.get(email) == null) {
            return "User not found.";
        }

        users.remove(email);
        return "User deleted.";
    }

    public String resetPassword(String email) {
        User user = users.get(email);
        if(user == null) {
            return "User not found.";
        }

        String randomPassword = UUID.randomUUID().toString();
        user.setPassword(randomPassword);

        return "Password reset, new one sent to email.";
    }
}

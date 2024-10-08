package org.example.service;

import org.example.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public String registerUser(String name, String email, String password) {
        if(users.containsKey(email)) {
            return "Пользователь с данными " + email + " уже существует.";
        }
        User newUser = new User(name, email, password);
        users.put(email, newUser);
        return "Пользователь зарегистрирован.";
    }

    public String loginUser(String email, String password) {
        User user = users.get(email);

        if(user == null) {
            return "Пользователь не найден.";
        }

        if(!user.getPassword().equals(password)) {
            return "Неверный пароль.";
        }

        return "Авторизация успешна.";
    }

    public String editUser(String email, String newName, String newEmail, String newPassword) {
        User user = users.get(email);
        if(user == null) {
            return "Пользователь не найден.";
        }

        if(users.containsKey(newEmail)) {
            return "Email уже используется.";
        }

        user.setName(newName);
        user.setEmail(newEmail);
        user.setPassword(newPassword);

        if(!email.equals(newEmail)) {
            users.remove(email);
            users.put(newEmail, user);
        }
        return "Данные обновлены.";
    }

    public String deleteUser(String email) {
        if(users.get(email) == null) {
            return "Пользователь не найден.";
        }

        users.remove(email);
        return "Пользователь удалён.";
    }

    public String resetPassword(String email) {
        User user = users.get(email);
        if(user == null) {
            return "Пользователь не найден.";
        }

        String randomPassword = UUID.randomUUID().toString();
        user.setPassword(randomPassword);

        return "Пароль сброшен, новый направлен на email.";
    }
}

package org.example;

import org.example.model.User;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UserServiceTest {

    private UserService userService;
    private Map<String, User> users;

    @BeforeEach
    public void setUp() {
        users = new HashMap<>();
        userService = new UserService(users);
    }

    @Test
    public void testRegisterUser() {
        String result = userService.registerUser("Kris", "kris@example.com", "password");
        assertThat(result).isEqualTo("Registration successful.");
        assertThat(users).containsKey("kris@example.com");
    }

    @Test
    public void testRegisterUserWithExistingEmail() {
        userService.registerUser("Kris", "kris@example.com", "password");
        String result = userService.registerUser("Kris", "kris@example.com", "password");
        assertThat(result).isEqualTo("Email is already in use.");
    }

    @Test
    public void testLoginUser() {
        userService.registerUser("Kris", "kris@example.com", "password");
        String result = userService.loginUser("kris@example.com", "password");
        assertThat(result).isEqualTo("Authorization successful.");
    }

    @Test
    public void testLoginUserWithIncorrectPassword() {
        userService.registerUser("Kris", "kris@example.com", "password");
        String result = userService.loginUser("kris@example.com", "wrongpassword");
        assertThat(result).isEqualTo("Wrong password.");
    }

    @Test
    public void testLoginNonExistentUser() {
        String result = userService.loginUser("kris@example.com", "password");
        assertThat(result).isEqualTo("User not found.");
    }

    @Test
    public void testCreateAdmin() {
        userService.createAdmin("admin", "admin@example.com", "admin");
        User admin = users.get("admin@example.com");
        assertThat(admin).isNotNull();
    }

    @Test
    public void testDeleteUser() {
        userService.registerUser("Kris", "kris@example.com", "password");
        String result = userService.deleteUser("kris@example.com");
        assertThat(result).isEqualTo("User deleted.");
        assertThat(users).doesNotContainKey("kris@example.com");
    }
}

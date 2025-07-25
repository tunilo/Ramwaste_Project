package org.example.data.saucedemo;

import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    private final Map<String, String> users = new HashMap<>();

    public DatabaseManager() {
    }

    @Step("Create Users Table")
    public void createUsersTable() {
        users.clear(); // Reset the in-memory "table"
    }

    @Step("Add User to Users Table")
    public void addUser(String username, String password) {
        users.put(username, password);
        System.out.println("Current users map: " + users.size());

    }

    @Step("Get User Password")
    public String getPassword(String username) {
        if (!users.containsKey(username)) {
            throw new RuntimeException("No user found with username: " + username);
        }
        return users.get(username);
    }

    @Step("Clear Users Table")
    public void clearUsersTable() {
        users.clear();
    }
}

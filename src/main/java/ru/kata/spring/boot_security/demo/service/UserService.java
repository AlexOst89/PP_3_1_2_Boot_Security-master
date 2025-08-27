package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void saveUser(User user, List<Long> selectedRolesIds);
    User getUser(int id);
    void deleteUser(int id);
    void updateUser(int id, User user, List<Long> selectedRolesIds);
}
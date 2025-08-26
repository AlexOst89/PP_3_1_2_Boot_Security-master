package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void saveUser(User user);
    User getUser(int id);
    void deleteUser(int id);
    void updateUser(int id, User user);

    List<Role> getAllRoles();
    Role getRole(long id);
}
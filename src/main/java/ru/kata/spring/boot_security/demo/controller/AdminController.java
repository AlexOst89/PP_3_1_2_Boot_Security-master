package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsersFromDao(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/addNewUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "addNewUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(required = false) List<Long> selectedRolesIds) {
        userService.saveUser(user, selectedRolesIds);
        return "redirect:/admin";
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam(value = "id") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "addNewUser";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("id") int id,
                             @RequestParam(required = false) List<Long> selectedRolesIds) {
        userService.updateUser(id, user, selectedRolesIds);
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
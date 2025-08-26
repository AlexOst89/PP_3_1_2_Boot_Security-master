package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.CustomUserDetailsService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AdminController(UserService userService, CustomUserDetailsService userDetailsService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public String getAllUsersFromDao(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/addNewUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userService.getAllRoles());
        return "addNewUser";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(required=false) List<Long> selectedRolesIds) {
        if(selectedRolesIds != null){
            List<Role> roles = new ArrayList<>();
            for(Long roleId : selectedRolesIds){
                roles.add(userService.getRole(roleId));
            }
            user.setRoles((Set<Role>) roles);
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/editUser")
    public String editUser(@RequestParam(value = "id") int id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", userService.getAllRoles());
        return "addNewUser";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("id") int id,
                             @RequestParam(required=false) List<Long> selectedRolesIds) {
        if(selectedRolesIds != null){
            List<Role> roles = new ArrayList<>();
            for(Long roleId : selectedRolesIds){
                roles.add(userService.getRole(roleId));
            }
            user.setRoles((Set<Role>) roles);
        }
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
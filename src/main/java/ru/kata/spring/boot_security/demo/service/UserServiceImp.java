package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.dao.RolesRepository;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

   private final UserRepository userRepository;
   private final RolesRepository rolesRepository;

   @Autowired
   public UserServiceImp(UserRepository userRepository, RolesRepository rolesRepository) {
      this.userRepository = userRepository;
      this.rolesRepository = rolesRepository;
   }

   @Override
   public List<User> getAllUsers() {
      return userRepository.findAll();
   }

   @Override
   public void saveUser(User user) {
      userRepository.save(user);
   }

   @Override
   public User getUser(int id) {
      Optional<User> optional = userRepository.findById(id);
      return optional.orElse(null);
   }

   @Override
   public void deleteUser(int id) {
      userRepository.deleteById(id);
   }

   @Override
   public void updateUser(int id, User updatedUser) {
      User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

      existingUser.setUsername(updatedUser.getUsername());
      existingUser.setPassword(updatedUser.getPassword());

      if (!updatedUser.getRoles().isEmpty()) {
         existingUser.getRoles().clear();
         existingUser.getRoles().addAll(updatedUser.getRoles());
      }

      userRepository.save(existingUser);
   }

   public List<Role> getAllRoles() {
      return rolesRepository.findAll();
   }

   public Role getRole(long id) {
      return rolesRepository.findById(id).orElse(null);
   }
}
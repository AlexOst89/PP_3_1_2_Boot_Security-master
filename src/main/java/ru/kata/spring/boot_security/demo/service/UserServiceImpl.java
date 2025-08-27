package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;

   @Autowired
   public UserServiceImpl(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   public List<User> getAllUsers() {
      return userRepository.findAll();
   }

   @Override
   public void saveUser(User user, List<Long> selectedRolesIds) {
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
   public void updateUser(int id, User updatedUser, List<Long> selectedRolesIds) {
      User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

      existingUser.setUsername(updatedUser.getUsername());
      existingUser.setPassword(updatedUser.getPassword());

      if (!updatedUser.getRoles().isEmpty()) {
         existingUser.getRoles().clear();
         existingUser.getRoles().addAll(updatedUser.getRoles());
      }

      userRepository.save(existingUser);
   }
}
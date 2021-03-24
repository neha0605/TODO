package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<User> getAllUser();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByName(String name);

    User createUser(User user);

}

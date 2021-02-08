package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> getAllUser();

    public Optional<User> getUserById(Long id);

    public User createUser(User user);
}

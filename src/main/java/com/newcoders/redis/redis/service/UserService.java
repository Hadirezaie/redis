package com.newcoders.redis.redis.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.newcoders.redis.redis.domain.User;
import com.newcoders.redis.redis.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        System.out.println("Fetching from DB...");
        // Fetch the user from the database
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void saveUser(User user) {
        // Save the user to the database
        System.out.println("Saving user: " + user);
        userRepository.save(user);
    }

    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Long id) {
        // Simulate deleting from the database
        userRepository.deleteById(id);
    }

    @CachePut(value = "users", key = "#user.id")
    public User updateUser(User user) {
        // Update the user in the database and return the updated user
        System.out.println("Updating user: " + user);
        return userRepository.save(user);
    }
}

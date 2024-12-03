// src/main/java/com/example/cs4750/services/UserService.java
package com.example.cs4750.services;

import com.example.cs4750.models.User;
import com.example.cs4750.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(String name, String email, String password, String bio) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassHash(hashPassword(password));
        user.setBio(bio);

        // Generate new UserID (you might want to use auto-increment in your database)
        Integer newUserId = userRepository.findMaxUserId().orElse(0) + 1;
        user.setUserId(newUserId);

        return userRepository.save(user);
    }

    public User login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            byte[] hashedPassword = hashPassword(password);
            if (MessageDigest.isEqual(hashedPassword, user.get().getPassHash())) {
                return user.get();
            }
        }
        return null;
    }

    private byte[] hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
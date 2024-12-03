package com.example.cs4750.repositories;

import com.example.cs4750.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    @Query("SELECT MAX(u.userId) FROM User u")
    Optional<Integer> findMaxUserId();
}

package com.example.cs4750.repositories;

import com.example.cs4750.models.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudioRepository extends JpaRepository<Studio, Integer> {
    List<Studio> findByStudioNameContainingIgnoreCase(String name);
}

package com.example.cs4750.repositories;

import com.example.cs4750.models.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<Writer, Integer> {
}

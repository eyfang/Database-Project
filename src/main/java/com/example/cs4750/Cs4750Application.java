package com.example.cs4750;

import com.example.cs4750.repositories.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Cs4750Application {

	public static void main(String[] args) {
		SpringApplication.run(Cs4750Application.class, args);
	}

	@Bean
	public CommandLineRunner testConnection(MovieRepository movieRepository) {
		return args -> {
			try {
				System.out.println("Testing database connection...");
				movieRepository.findAll();
				System.out.println("Successfully connected to database!");
			} catch (Exception e) {
				System.err.println("Failed to connect to database: " + e.getMessage());
				e.printStackTrace();
			}
		};
	}
}
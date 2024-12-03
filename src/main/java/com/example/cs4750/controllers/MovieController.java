package com.example.cs4750.controllers;

import com.example.cs4750.models.Movie;
import com.example.cs4750.services.MovieService;
import com.example.cs4750.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        System.out.println("Found " + movies.size() + " movies");
        return movies;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@PathVariable Integer id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMovies(@RequestParam String title) {
        return ResponseEntity.ok(movieService.searchMoviesByTitle(title));
    }

    @GetMapping("/{id}/rating")
    public ResponseEntity<?> getMovieRating(@PathVariable Integer id) {
        Double rating = movieService.getMovieRating(id);
        return rating != null ? ResponseEntity.ok(rating) : ResponseEntity.notFound().build();
    }

    @GetMapping("/test")
    public ResponseEntity<String> testConnection() {
        try {
            List<Movie> movies = movieRepository.findAll();
            return ResponseEntity.ok("Successfully connected to database. Found " + movies.size() + " movies.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Database connection failed: " + e.getMessage());
        }
    }
}

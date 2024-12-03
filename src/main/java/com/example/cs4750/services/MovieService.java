package com.example.cs4750.services;

import com.example.cs4750.models.Director;
import com.example.cs4750.models.Movie;
import com.example.cs4750.models.Studio;
import com.example.cs4750.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private DirectorRepository directorRepository;

    public List<Movie> getAllMovies() {
        List<Movie> movies = movieRepository.findAll(); // Fetch all movies
        movies.forEach(movie -> {
            Double avgRating = reviewRepository.getAverageRatingForMovie(movie.getMovieId());
            movie.setAverageRating(avgRating); // Set the transient field
        });
        return movies;
    }

    public Optional<Movie> getMovieById(Integer id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        movieOptional.ifPresent(movie -> {
            Double avgRating = reviewRepository.getAverageRatingForMovie(movie.getMovieId());
            movie.setAverageRating(avgRating);
        });
        return movieOptional;
    }


    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public Double getMovieRating(Integer movieId) {
        return reviewRepository.getAverageRatingForMovie(movieId);
    }

    public List<Movie> getMoviesWithRatings() {
        List<Movie> movies = movieRepository.findAll();
        movies.forEach(movie -> {
            Double avgRating = reviewRepository.getAverageRatingForMovie(movie.getMovieId());
            movie.setAverageRating(avgRating);
        });
        return movies;
    }

    public List<Movie> filterMovies(Integer year, String studioName, String directorName) {
        return movieRepository.findMoviesByFilter(year, studioName, directorName);
    }

}
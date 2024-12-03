package com.example.cs4750.repositories;

import com.example.cs4750.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT m FROM Movie m WHERE m.year = ?1")
    List<Movie> findByYear(Integer year);

    @Query("SELECT AVG(r.ratingValue) FROM Review r WHERE r.movieId = :movieId")
    Double getAverageRatingForMovie(@Param("movieId") Integer movieId);

}
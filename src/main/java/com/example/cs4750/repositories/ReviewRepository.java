package com.example.cs4750.repositories;

import com.example.cs4750.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r WHERE r.movieId = :movieId")
    List<Review> findByMovieId(@Param("movieId") Integer movieId);

    @Query("SELECT MAX(r.reviewId) FROM Review r")
    Optional<Integer> findMaxReviewId();
    //test
}
package com.example.cs4750.repositories;

import com.example.cs4750.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findByMovieId(Integer movieId);

    @Query("SELECT MAX(r.ratingId) FROM Rating r")
    Optional<Integer> findMaxRatingId();

    @Query("SELECT AVG(r.ratingValue) FROM Rating r WHERE r.movieId = :movieId")
    Double getAverageRatingForMovie(@Param("movieId") Integer movieId);
}
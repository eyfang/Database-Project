package com.example.cs4750.repositories;

import com.example.cs4750.models.Director;
import com.example.cs4750.models.Movie;
import com.example.cs4750.models.Studio;
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

    @Query("SELECT m FROM Movie m " +
            "JOIN m.studio s " +
            "JOIN m.director d " +
            "WHERE (:year IS NULL OR m.year = :year) " +
            "AND (:studioName IS NULL OR LOWER(s.studioName) LIKE LOWER(CONCAT('%', :studioName, '%'))) " +
            "AND (:directorName IS NULL OR LOWER(d.person.name) LIKE LOWER(CONCAT('%', :directorName, '%')))")
    List<Movie> findMoviesByFilter(
            @Param("year") Integer year,
            @Param("studioName") String studioName,
            @Param("directorName") String directorName);

}
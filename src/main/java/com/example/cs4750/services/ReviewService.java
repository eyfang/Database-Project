// src/main/java/com/example/cs4750/services/ReviewService.java
package com.example.cs4750.services;

import com.example.cs4750.models.Review;
import com.example.cs4750.models.Rating;
import com.example.cs4750.repositories.ReviewRepository;
import com.example.cs4750.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RatingRepository ratingRepository;

    public Review createReview(Integer userId, Integer movieId, String reviewText, Integer ratingValue) {
        System.out.println("Creating review with rating: " + ratingValue); // Debug print

        // Create Review
        Review review = new Review();
        Integer newReviewId = reviewRepository.findMaxReviewId().orElse(0) + 1;
        review.setReviewId(newReviewId);
        review.setUserId(userId);
        review.setMovieId(movieId);
        review.setDate(LocalDate.now());
        review.setReviewText(reviewText);

        Review savedReview = reviewRepository.save(review);
        System.out.println("Saved review with ID: " + savedReview.getReviewId()); // Debug print

        // Create Rating
        if (ratingValue != null && ratingValue > 0) {
            try {
                Rating rating = new Rating();
                Integer newRatingId = ratingRepository.findMaxRatingId().orElse(0) + 1;
                rating.setRatingId(newRatingId);
                rating.setReviewId(savedReview.getReviewId());
                rating.setUserId(userId);
                rating.setMovieId(movieId);
                rating.setRatingValue(ratingValue);
                Rating savedRating = ratingRepository.save(rating);
                System.out.println("Saved rating with ID: " + savedRating.getRatingId()); // Debug print
            } catch (Exception e) {
                System.err.println("Error saving rating: " + e.getMessage()); // Debug print
                e.printStackTrace();
            }
        }

        return reviewRepository.findById(savedReview.getReviewId()).orElse(null);
    }

    public List<Review> getMovieReviews(Integer movieId) {
        return reviewRepository.findByMovieId(movieId);
    }
}
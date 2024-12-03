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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;


    public Review createReview(Integer userId, Integer movieId, String reviewText, Integer ratingValue) {
        System.out.println("Creating review with rating: " + ratingValue);

        Review review = new Review();
        Integer newReviewId = reviewRepository.findMaxReviewId().orElse(0) + 1;
        review.setReviewId(newReviewId);
        review.setUserId(userId);
        review.setMovieId(movieId);
        review.setDate(LocalDate.now());
        review.setReviewText(reviewText);
        review.setRatingValue(ratingValue);

        return reviewRepository.save(review);
    }

    public List<Review> getMovieReviews(Integer movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    public boolean deleteReview(Integer reviewId, Integer userId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            if (review.getUserId().equals(userId)) {
                reviewRepository.delete(review);
                return true;
            }
        }
        return false;
    }


}
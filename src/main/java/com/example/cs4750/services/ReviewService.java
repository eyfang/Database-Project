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
        System.out.println("Creating review with rating: " + ratingValue);

        // Create and save Review
        Review review = new Review();
        Integer newReviewId = reviewRepository.findMaxReviewId().orElse(0) + 1;
        review.setReviewId(newReviewId);
        review.setUserId(userId);
        review.setMovieId(movieId);
        review.setDate(LocalDate.now());
        review.setReviewText(reviewText);
        review.setRatingValue(ratingValue); // Set embedded rating value

        return reviewRepository.save(review);
    }


    public List<Review> getMovieReviews(Integer movieId) {
        return reviewRepository.findByMovieId(movieId);
//        List<Review> reviews = reviewRepository.findByMovieId(movieId);
//        reviews.forEach(review -> {
//            Rating rating = ratingRepository.findByReviewId(review.getReviewId()).orElse(null); // Fetch associated rating
//            review.setRating(rating);
//        });
//        return reviews;
    }

}
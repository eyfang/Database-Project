package com.example.cs4750.controllers;

import com.example.cs4750.dtos.ReviewRequest;
import com.example.cs4750.models.Review;
import com.example.cs4750.repositories.ReviewRepository;
import com.example.cs4750.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getMovieReviews(@PathVariable Integer movieId) {
        try {
            List<Review> reviews = reviewService.getMovieReviews(movieId);
            reviews.forEach(review -> {
                System.out.println("Review ID: " + review.getReviewId()
                        + " Rating: " + (review.getRating() != null ? review.getRating().getRatingValue() : "no rating"));
            });
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest request) {
        try {
            Review review = reviewService.createReview(
                    request.getUserId(),
                    request.getMovieId(),
                    request.getReviewText(),
                    request.getRating()
            );
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

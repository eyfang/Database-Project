// src/main/java/com/example/cs4750/dto/ReviewRequest.java
package com.example.cs4750.dtos;

public class ReviewRequest {
    private Integer userId;
    private Integer movieId;
    private String reviewText;
    private Integer rating;

    // Getters and setters
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getMovieId() { return movieId; }
    public void setMovieId(Integer movieId) { this.movieId = movieId; }
    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
}

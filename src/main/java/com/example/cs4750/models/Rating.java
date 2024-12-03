package com.example.cs4750.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Rating")
@Data
public class Rating {
    @Id
    @Column(name = "RatingID")
    private Integer ratingId;

    @Column(name = "ReviewID")
    private Integer reviewId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "MovieID")
    private Integer movieId;

    @Column(name = "rating_value")
    private Integer ratingValue;

    @ManyToOne
    @JoinColumn(name = "ReviewID", insertable = false, updatable = false)
    private Review review;
}
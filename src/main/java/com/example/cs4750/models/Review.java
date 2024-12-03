package com.example.cs4750.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Review")
public class Review {
    @Id
    @Column(name = "ReviewID")
    private Integer reviewId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "MovieID")
    private Integer movieId;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "ReviewText")
    private String reviewText;

    @Column(name = "rating_value")
    private Integer ratingValue;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "MovieID", insertable = false, updatable = false)
    private Movie movie;
}
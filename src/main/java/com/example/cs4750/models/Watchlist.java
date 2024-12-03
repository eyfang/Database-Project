package com.example.cs4750.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Watchlist")
public class Watchlist {
    @Id
    @Column(name = "WatchListID")
    private Integer watchListId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "MovieID")
    private Integer movieId;

    @ManyToOne
    @JoinColumn(name = "MovieID", insertable = false, updatable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;
}
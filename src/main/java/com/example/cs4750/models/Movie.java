package com.example.cs4750.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Movie")
public class Movie {
    @Id
    @Column(name = "MovieID")
    private Integer movieId;

    @ManyToOne
    @JoinColumn(name = "StudioID")
    private Studio studio;

    @Column(name = "Title")
    private String title;

    @Column(name = "Year")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "DirectorID")
    private Director director;

    @ManyToOne
    @JoinColumn(name = "WriterID")
    private Writer writer;

    @Transient
    private Double averageRating;
}
package com.example.cs4750.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Studio")
public class Studio {
    @Id
    @Column(name = "StudioID")
    private Integer studioId;

    @Column(name = "StudioName")
    private String studioName;

    @Column(name = "DateFounded")
    private LocalDate dateFounded;

    @Column(name = "StudioHead")
    private String studioHead;
}
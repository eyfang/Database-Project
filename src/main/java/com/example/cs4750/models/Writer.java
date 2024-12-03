package com.example.cs4750.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Writer")
public class Writer {
    @Id
    @Column(name = "person_id")
    private Integer personId;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
}

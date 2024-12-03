package com.example.cs4750.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "PersonID")
    private Integer personID;

    @Column(name = "Name")
    private String name;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "Age")
    private Integer age;
}

package com.example.cs4750.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "[User]") // Square brackets because User is a reserved word
public class User {
    @Id
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    private String email;

    @Column(name = "PassHash")
    private byte[] passHash;

    @Column(name = "Bio")
    private String bio;
}
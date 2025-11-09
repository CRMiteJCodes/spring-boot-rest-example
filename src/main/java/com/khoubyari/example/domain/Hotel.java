package com.khoubyari.example.domain;

import jakarta.persistence.*;
import lombok.*;

//Modernized domain entity

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    private String city;
    private int rating;
}
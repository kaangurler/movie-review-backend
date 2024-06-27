package com.example.movieservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String title;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "movie_category",
            joinColumns = @JoinColumn(name = "movie_id"))
    @Enumerated(EnumType.STRING)
    private List<Category> categories;

    @ElementCollection
    @CollectionTable(name = "movie_cast",
            joinColumns = @JoinColumn(name = "movie_id"))
    private List<Long> casts;
    private Integer releaseYear;
    private Integer duration;
    @Lob
    @Column(length = 512000)
    private String storyline;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rating> ratings;
    private byte[] image;
}

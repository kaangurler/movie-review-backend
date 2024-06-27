package com.example.movieservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieResponse {
    private Long id;
    private String title;
    private List<String> categories;
    private List<Long> casts;
    private Integer releaseYear;
    private Integer duration;
    private String storyline;
    private Double rating;
    private byte[] image;
}

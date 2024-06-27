package com.example.movieservice.dto;

import com.example.movieservice.entity.Movie;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingResponse {
    private Long id;
    private Long userId;
    private Movie movie;
    private Integer value;
}

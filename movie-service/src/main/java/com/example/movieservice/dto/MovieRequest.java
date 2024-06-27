package com.example.movieservice.dto;

import com.example.movieservice.entity.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieRequest {
    @NotBlank(message = "Title cannot be empty")
    private String title;
    @NotEmpty(message = "Categories cannot be empty")
    private List<Category> categories;
    @NotNull(message = "Release Year cannot be empty")
    private Integer releaseYear;
    @DecimalMin(value = "0", message = "Duration cannot be less than 0")
    @NotNull(message = "Duration cannot be empty")
    private Integer duration;
    private String storyline;
}

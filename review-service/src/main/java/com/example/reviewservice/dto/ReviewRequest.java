package com.example.reviewservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequest {
    @NotNull(message = "User ID cannot be empty")
    private Long userId;
    @NotNull(message = "Movie ID cannot be empty")
    private Long movieId;
    @NotBlank(message = "Comment cannot be empty")
    private String comment;
    @NotNull(message = "Spoiler cannot be empty")
    private Boolean spoiler;
}

package com.example.reviewservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReviewResponse {
    private Long id;
    private Long userId;
    private Long movieId;
    private String comment;
    private Boolean edited;
    private Boolean spoiler;
}

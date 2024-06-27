package com.example.reviewservice.util;

import com.example.reviewservice.dto.ReviewRequest;
import com.example.reviewservice.dto.ReviewResponse;
import com.example.reviewservice.entity.Review;

import java.util.Date;

public class Mapper {
    public static ReviewResponse toReviewResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUserId())
                .movieId(review.getMovieId())
                .comment(review.getComment())
                .edited(review.getEdited())
                .spoiler(review.getSpoiler())
                .build();
    }

    public static Review toReview(ReviewRequest reviewRequest) {
        return Review.builder()
                .userId(reviewRequest.getUserId())
                .movieId(reviewRequest.getMovieId())
                .comment(reviewRequest.getComment())
                .creationTime(new Date(System.currentTimeMillis()))
                .edited(false)
                .spoiler(reviewRequest.getSpoiler())
                .build();
    }
}

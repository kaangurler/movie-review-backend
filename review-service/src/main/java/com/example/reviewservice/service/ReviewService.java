package com.example.reviewservice.service;

import com.example.reviewservice.dto.ReviewRequest;
import com.example.reviewservice.dto.ReviewResponse;
import com.example.reviewservice.entity.Review;
import com.example.reviewservice.exception.custom.MovieNotFoundException;
import com.example.reviewservice.exception.custom.ReviewNotFoundException;
import com.example.reviewservice.exception.custom.UserNotFoundException;
import com.example.reviewservice.repository.ReviewRepository;
import com.example.reviewservice.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final WebClient.Builder webClientBuilder;

    public void create(ReviewRequest reviewRequest) {
        validateUserId(reviewRequest.getUserId());
        validateMovieId(reviewRequest.getMovieId());
        reviewRepository.save(Mapper.toReview(reviewRequest));
    }

    public ReviewResponse getById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(String.valueOf(id)));
        return Mapper.toReviewResponse(review);
    }

    public List<ReviewResponse> getByUserId(Long userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(Mapper::toReviewResponse)
                .toList();
    }

    public List<ReviewResponse> getByMovieId(Long movieId) {
        return reviewRepository.findByMovieId(movieId).stream()
                .map(Mapper::toReviewResponse)
                .toList();
    }

    public List<ReviewResponse> getAll() {
        return reviewRepository.findAll().stream()
                .map(Mapper::toReviewResponse)
                .toList();
    }

    public List<ReviewResponse> sortByCreationTime(List<Review> reviews) {
        return reviews
                .stream()
                .sorted(Comparator.comparing(Review::getCreationTime).reversed())
                .map(Mapper::toReviewResponse)
                .toList();
    }

    public List<ReviewResponse> getAllByMovieIdSortByCreationTime(Long movieId) {
        return sortByCreationTime(reviewRepository.findByMovieId(movieId));
    }

    public List<ReviewResponse> getAllByUserIdSortByCreationTime(Long userId) {
        return sortByCreationTime(reviewRepository.findByUserId(userId));
    }

    public void update(Long id, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(String.valueOf(id)));
        review.setComment(reviewRequest.getComment());
        review.setEdited(true);
        review.setSpoiler(reviewRequest.getSpoiler());
        reviewRepository.save(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public void validateUserId(Long userId) {
        try {
            webClientBuilder
                    .baseUrl("http://localhost:8080/user/v1")
                    .build()
                    .get()
                    .uri("/validate/userId=" + userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            throw new UserNotFoundException(String.valueOf(userId));
        }
    }

    public void validateMovieId(Long movieId) {
        try {
            webClientBuilder
                    .baseUrl("http://localhost:8081/movie/v1")
                    .build()
                    .get()
                    .uri("/validate/movieId=" + movieId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            throw new MovieNotFoundException(String.valueOf(movieId));
        }
    }
}

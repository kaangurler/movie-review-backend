package com.example.reviewservice.controller;

import com.example.reviewservice.dto.ReviewRequest;
import com.example.reviewservice.dto.ReviewResponse;
import com.example.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review/v1")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/")
    public void create(@Valid @RequestBody ReviewRequest reviewRequest) {
        reviewService.create(reviewRequest);
    }

    @GetMapping("/id={id}")
    public ResponseEntity<ReviewResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getById(id));
    }

    @GetMapping("/userId={userId}")
    public ResponseEntity<List<ReviewResponse>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getByUserId(userId));
    }

    @GetMapping("/movieId={movieId}")
    public ResponseEntity<List<ReviewResponse>> getByMovieId(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getByMovieId(movieId));
    }

    @GetMapping("/userId={userId}&sort=creationTime")
    public ResponseEntity<List<ReviewResponse>> getAllByUserIdSortByCreationTime(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getAllByUserIdSortByCreationTime(userId));
    }

    @GetMapping("/movieId={movieId}&sort=creationTime")
    public ResponseEntity<List<ReviewResponse>> getAllByMovieIdSortByCreationTime(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getAllByMovieIdSortByCreationTime(movieId));
    }

    @GetMapping("/")
    public ResponseEntity<List<ReviewResponse>> getAll() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    @PutMapping("/id={id}")
    public void update(@PathVariable Long id, @Valid @RequestBody ReviewRequest reviewRequest) {
        reviewService.update(id, reviewRequest);
    }

    @DeleteMapping("/id={id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(id);
    }
}

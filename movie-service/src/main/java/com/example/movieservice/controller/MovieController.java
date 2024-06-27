package com.example.movieservice.controller;

import com.example.movieservice.dto.MovieResponse;
import com.example.movieservice.dto.RatingRequest;
import com.example.movieservice.dto.RatingResponse;
import com.example.movieservice.entity.Category;
import com.example.movieservice.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie/v1")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/id={id}")
    public ResponseEntity<MovieResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(movieService.getById(id));
    }

    @GetMapping("/sort=a-z")
    public ResponseEntity<List<MovieResponse>> getAllSortByTitle() {
        return ResponseEntity.ok(movieService.getAllSortByTitle());
    }

    @GetMapping("/sort=rating")
    public ResponseEntity<List<MovieResponse>> getAllSortByRating() {
        return ResponseEntity.ok(movieService.getAllSortByRating());
    }

    @GetMapping("/sort=a-z&category={category}")
    public ResponseEntity<List<MovieResponse>> getByCategorySortByTitle(@PathVariable Category category) {
        return ResponseEntity.ok(movieService.getByCategorySortByTitle(category));
    }

    @GetMapping("/sort=rating&category={category}")
    public ResponseEntity<List<MovieResponse>> getByCategorySortByRating(@PathVariable Category category) {
        return ResponseEntity.ok(movieService.getByCategorySortByRating(category));
    }

    @GetMapping("/castId={castId}")
    public ResponseEntity<List<MovieResponse>> getByCastId(@PathVariable Long castId) {
        return ResponseEntity.ok(movieService.getByCastId(castId));
    }

    @GetMapping("/castId={castId}&sort=releaseYear")
    public ResponseEntity<List<MovieResponse>> getByCastIdSortByReleaseYear(@PathVariable Long castId) {
        return ResponseEntity.ok(movieService.getByCastIdSortByReleaseYear(castId));
    }

    @GetMapping("/sort=rating&limit={numberOfMovies}")
    public ResponseEntity<List<MovieResponse>> getAllSortedByRatingLimitedByNumberOfMovies(@PathVariable Integer numberOfMovies) {
        return ResponseEntity.ok(movieService.getAllSortedByRatingLimitedByNumberOfMovies(numberOfMovies));
    }

    @GetMapping("/sort=releaseYear&limit={numberOfMovies}")
    public ResponseEntity<List<MovieResponse>> getAllSortedByReleaseYearLimitedByNumberOfMovies(@PathVariable Integer numberOfMovies) {
        return ResponseEntity.ok(movieService.getAllSortedByReleaseYearLimitedByNumberOfMovies(numberOfMovies));
    }

    @GetMapping("/sort=numberOfRatings&limit={numberOfMovies}")
    public ResponseEntity<List<MovieResponse>> getAllSortByNumberOfRatingsLimitedByNumberOfMovies(@PathVariable Integer numberOfMovies) {
        return ResponseEntity.ok(movieService.getAllSortByNumberOfRatingsLimitedByNumberOfMovies(numberOfMovies));
    }

    @GetMapping("/")
    public ResponseEntity<List<MovieResponse>> getAll() {
        return ResponseEntity.ok(movieService.getAll());
    }

    @GetMapping("/validate/movieId={movieId}")
    public ResponseEntity<Boolean> validateMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.validateMovie(movieId));
    }

    @GetMapping("/ratings/get-ratings/userId={userId}")
    public ResponseEntity<List<RatingResponse>> getRatingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(movieService.getRatingsByUserId(userId));
    }

    @PutMapping("/rate/movieId={movieId}")
    public void rateMovie(@PathVariable Long movieId, @Valid @RequestBody RatingRequest ratingRequest) {
        System.out.println("HERE1");
        movieService.rateMovie(movieId, ratingRequest);
    }
}

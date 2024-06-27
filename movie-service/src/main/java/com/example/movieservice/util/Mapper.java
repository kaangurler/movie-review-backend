package com.example.movieservice.util;

import com.example.movieservice.dto.MovieRequest;
import com.example.movieservice.dto.MovieResponse;
import com.example.movieservice.dto.RatingRequest;
import com.example.movieservice.dto.RatingResponse;
import com.example.movieservice.entity.Category;
import com.example.movieservice.entity.Movie;
import com.example.movieservice.entity.Rating;

import java.util.List;

public class Mapper {
    public static MovieResponse toMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .categories(getCategoryTitleList(movie.getCategories()))
                .casts(movie.getCasts())
                .releaseYear(movie.getReleaseYear())
                .duration(movie.getDuration())
                .storyline(movie.getStoryline())
                .rating(Calculator.getAverageRating(movie.getRatings()))
                .image(movie.getImage())
                .build();
    }

    public static Movie toMovie(MovieRequest movieRequest) {
        return Movie.builder()
                .title(movieRequest.getTitle())
                .categories(movieRequest.getCategories())
                .releaseYear(movieRequest.getReleaseYear())
                .duration(movieRequest.getDuration())
                .storyline(movieRequest.getStoryline())
                .build();
    }

    public static RatingResponse toRatingResponse(Rating rating) {
        return RatingResponse.builder()
                .id(rating.getId())
                .userId(rating.getUserId())
                .value(rating.getValue())
                .movie(rating.getMovie())
                .build();
    }

    public static Rating toRating(RatingRequest ratingRequest) {
        return Rating.builder()
                .userId(ratingRequest.getUserId())
                .value(ratingRequest.getValue())
                .build();
    }

    public static List<String> getCategoryTitleList(List<Category> categories) {
        return categories.stream()
                .map(Category::getTitle)
                .toList();
    }
}

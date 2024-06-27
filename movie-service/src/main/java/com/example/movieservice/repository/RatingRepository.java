package com.example.movieservice.repository;

import com.example.movieservice.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByUserId(Long userId);
    List<Rating> findAllByMovieId(Long movieId);
}

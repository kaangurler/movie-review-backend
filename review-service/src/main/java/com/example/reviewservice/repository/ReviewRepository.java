package com.example.reviewservice.repository;

import com.example.reviewservice.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);
    List<Review> findByMovieId(Long movieId);
    /*@Query("SELECT r FROM Review r WHERE c = :userId")
    List<Review> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Review r WHERE c = :movieId")
    List<Review> findAllByMovieId(@Param("movieId") Long movieID);*/
}

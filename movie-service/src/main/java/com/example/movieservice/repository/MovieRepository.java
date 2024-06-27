package com.example.movieservice.repository;

import com.example.movieservice.entity.Category;
import com.example.movieservice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT m FROM Movie m JOIN m.categories c WHERE c = :category")
    List<Movie> findByCategory(@Param("category") Category category);
    @Query("SELECT m FROM Movie m JOIN m.casts c WHERE c = :castId")
    List<Movie> findByCastId(@Param("castId") Long castId);
}

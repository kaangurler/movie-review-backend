package com.example.castservice.repository;

import com.example.castservice.entity.Cast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CastRepository extends JpaRepository<Cast, Long> {
}

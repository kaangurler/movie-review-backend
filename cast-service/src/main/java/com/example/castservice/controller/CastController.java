package com.example.castservice.controller;

import com.example.castservice.dto.CastResponse;
import com.example.castservice.service.CastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cast/v1")
@RequiredArgsConstructor
public class CastController {
    private final CastService castService;

    @GetMapping("/id={id}")
    public ResponseEntity<CastResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(castService.getById(id));
    }

    @GetMapping("/movieId={movieId}")
    public ResponseEntity<List<CastResponse>> getAllByMovieId(@PathVariable Long movieId) {
        return ResponseEntity.ok(castService.getAllByMovieId(movieId));
    }

    @GetMapping("/")
    public ResponseEntity<List<CastResponse>> getAll() {
        return ResponseEntity.ok(castService.getAll());
    }

    @GetMapping("/sort=a-z")
    public ResponseEntity<List<CastResponse>> getAllSortByAz() {
        return ResponseEntity.ok(castService.getAllSortByAz());
    }

    @GetMapping("/validate/id={id}")
    public ResponseEntity<Boolean> validateCastExists(@PathVariable Long id) {
        return ResponseEntity.ok(castService.validateCastExists(id));
    }
}
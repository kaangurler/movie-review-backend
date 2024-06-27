package com.example.castservice.controller;

import com.example.castservice.dto.CastRequest;
import com.example.castservice.dto.MovieResponse;
import com.example.castservice.service.CastService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/cast/admin/v1")
@RequiredArgsConstructor
public class CastAdminController {
    private final CastService castService;

    @PostMapping("/")
    public void create(@Valid @RequestBody CastRequest castRequest) {
        castService.create(castRequest);
    }

    @PutMapping("/id={id}")
    public void update(@PathVariable Long id, @Valid @RequestBody CastRequest castRequest) {
        castService.update(id, castRequest);
    }

    @PutMapping("/add-movie/castId={castId}")
    public ResponseEntity<Boolean> addMovie(@PathVariable Long castId, @RequestBody MovieResponse movieResponse) {
        castService.addMovie(castId, movieResponse);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/remove-movie/castId={castId}")
    public ResponseEntity<Boolean> removeMovie(@PathVariable Long castId, @RequestBody MovieResponse movieResponse) {
        castService.removeMovie(castId, movieResponse);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/upload-photo/castId={castId}")
    public void uploadPhoto(@RequestParam("imageFile") MultipartFile imageFile, @PathVariable Long castId) throws IOException {
        castService.uploadPoster(imageFile, castId);
    }

    @DeleteMapping("/id={id}")
    public void delete(@PathVariable Long id) {
        castService.delete(id);
    }
}

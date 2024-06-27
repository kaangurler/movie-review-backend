package com.example.movieservice.controller;

import com.example.movieservice.dto.MovieRequest;
import com.example.movieservice.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/movie/admin/v1")
@RequiredArgsConstructor
public class MovieAdminController {
    private final MovieService movieService;

    @PostMapping("/")
    public void create(@Valid @RequestBody MovieRequest movieRequest) {
        movieService.create(movieRequest);
    }

    @PutMapping("/update/movieId={id}")
    public void updateById(@Valid @PathVariable long id, @RequestBody MovieRequest movieRequest) {
        movieService.updateById(id, movieRequest);
    }

    @PutMapping("/add-cast/movieId={movieId}/castId={castId}")
    public void addCastMember(@PathVariable Long movieId, @PathVariable Long castId) {
        movieService.addCastMember(movieId, castId);
    }

    @PutMapping("/remove-cast/movieId={movieId}/castId={castId}")
    public void removeCastMember(@PathVariable Long movieId, @PathVariable Long castId) {
        movieService.removeCastMember(movieId, castId);
    }

    @DeleteMapping("/remove-cast/castId={castId}")
    public void removeCastMemberFromAllMovies(@PathVariable Long castId) {
        movieService.removeCastMemberFromAllMovies(castId);
    }

    @PutMapping("/upload-poster/movieId={movieId}")
    public void uploadPoster(@RequestParam("imageFile") MultipartFile imageFile, @PathVariable Long movieId) throws IOException {
        movieService.uploadPoster(imageFile, movieId);
    }

    @DeleteMapping("/id={id}")
    public void deleteById(@PathVariable long id) {
        movieService.deleteById(id);
    }
}

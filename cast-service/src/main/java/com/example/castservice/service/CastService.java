package com.example.castservice.service;

import com.example.castservice.dto.CastRequest;
import com.example.castservice.dto.CastResponse;
import com.example.castservice.dto.MovieResponse;
import com.example.castservice.entity.Cast;
import com.example.castservice.exception.custom.CastNotFoundException;
import com.example.castservice.repository.CastRepository;
import com.example.castservice.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CastService {
    private final CastRepository castRepository;
    private final WebClient.Builder webClientBuilder;

    public void create(CastRequest castRequest) {
        castRepository.save(Mapper.toCast(castRequest));
    }

    public CastResponse getById(Long id) {
        Cast cast = castRepository.findById(id)
                .orElseThrow(() -> new CastNotFoundException(String.valueOf(id)));
        return Mapper.toCastResponse(cast);
    }

    public List<CastResponse> getAll() {
        return castRepository.findAll().stream()
                .map(Mapper::toCastResponse)
                .toList();
    }

    public List<CastResponse> getAllSortByAz() {
        return castRepository.findAll().stream()
                .map(Mapper::toCastResponse)
                .sorted(Comparator.comparing(castResponse -> castResponse.getName() + castResponse.getSurname()))
                .toList();
    }

    public void update(Long id, CastRequest castRequest) {
        Cast cast = castRepository.findById(id)
                .orElseThrow(() -> new CastNotFoundException(String.valueOf(id)));
        cast.setName(castRequest.getName());
        cast.setSurname(castRequest.getSurname());
        cast.setAge(castRequest.getAge());
        castRepository.save(cast);
    }

    public void addMovie(Long castId, MovieResponse movieResponse) {
        Cast cast = castRepository.findById(castId)
                .orElseThrow(() -> new CastNotFoundException(String.valueOf(castId)));
        List<Long> movies = cast.getMovies();
        if (!movies.contains(movieResponse.getId())) {
            movies.add(movieResponse.getId());
            cast.setMovies(movies);
        }
        castRepository.save(cast);
    }

    public void removeMovie(Long castId, MovieResponse movieResponse) {
        Cast cast = castRepository.findById(castId)
                .orElseThrow(() -> new CastNotFoundException(String.valueOf(castId)));
        List<Long> movies = cast.getMovies();
        if (movies.contains(movieResponse.getId())) {
            movies.remove(movieResponse.getId());
            cast.setMovies(movies);
        }
        castRepository.save(cast);
    }

    public void delete(Long id) {
        removeCastFromMovies(id);
        castRepository.deleteById(id);
    }

    public Boolean validateCastExists(Long id) {
        Cast cast = castRepository.findById(id)
                .orElseThrow(() -> new CastNotFoundException(String.valueOf(id)));
        return cast != null;
    }

    public void removeCastFromMovies(Long id) {
        try {
            webClientBuilder.baseUrl("http://localhost:8081/movie/admin/v1")
                    .build()
                    .delete()
                    .uri("/remove-cast/castId=" + id)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            throw new CastNotFoundException(String.valueOf(id));
        }
    }

    public void uploadPoster(MultipartFile imageFile, Long castId) throws IOException {
        Cast cast = castRepository.findById(castId)
                .orElseThrow(() -> new CastNotFoundException(String.valueOf(castId)));
        cast.setPhoto(imageFile.getBytes());
        castRepository.save(cast);
    }

    public List<CastResponse> getAllByMovieId(Long movieId) {
        return castRepository.findAll().stream()
                .filter(cast -> cast.getMovies()
                        .contains(movieId))
                .map(Mapper::toCastResponse)
                .toList();
    }
}

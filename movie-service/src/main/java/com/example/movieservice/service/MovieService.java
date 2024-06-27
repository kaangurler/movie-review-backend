package com.example.movieservice.service;

import com.example.movieservice.dto.MovieRequest;
import com.example.movieservice.dto.MovieResponse;
import com.example.movieservice.dto.RatingRequest;
import com.example.movieservice.dto.RatingResponse;
import com.example.movieservice.entity.Category;
import com.example.movieservice.entity.Movie;
import com.example.movieservice.entity.Rating;
import com.example.movieservice.exception.custom.CastNotFoundException;
import com.example.movieservice.exception.custom.MovieNotFoundException;
import com.example.movieservice.exception.custom.RatingFailedException;
import com.example.movieservice.exception.custom.UserNotFoundException;
import com.example.movieservice.repository.MovieRepository;
import com.example.movieservice.repository.RatingRepository;
import com.example.movieservice.util.Mapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;
    private final WebClient.Builder webClientBuilder;

    public void create(MovieRequest movieRequest) {
        movieRequest.setCategories(movieRequest
                .getCategories()
                .stream()
                .sorted(Comparator.comparing(Category::getTitle))
                .toList());
        movieRepository.save(Mapper.toMovie(movieRequest));
    }

    public MovieResponse getById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(String.valueOf(id)));
        return Mapper.toMovieResponse(movie);
    }

    public List<MovieResponse> getByCastId(Long castId) {
        return movieRepository.findByCastId(castId).stream()
                .map(Mapper::toMovieResponse)
                .toList();
    }

    public List<MovieResponse> getByCastIdSortByReleaseYear(Long castId) {
        return sortByReleaseYear(movieRepository.findByCastId(castId));
    }

    public List<MovieResponse> getByCategorySortByTitle(Category category) {
        return sortByTitle(movieRepository.findByCategory(category));
    }

    public List<MovieResponse> getByCategorySortByRating(Category category) {
        return sortByRating(movieRepository.findByCategory(category));
    }

    public List<MovieResponse> getAllSortByTitle() {
        return sortByTitle(movieRepository.findAll());
    }

    public List<MovieResponse> getAllSortByRating() {
        return sortByRating(movieRepository.findAll());
    }

    public List<MovieResponse> getAllSortByNumberOfRatings() {
        return movieRepository.findAll().stream()
                .sorted(Comparator.comparingInt((Movie movie) -> movie.getRatings().size()).reversed())
                .map(Mapper::toMovieResponse)
                .toList();
    }

    public List<MovieResponse> getAllSortByNumberOfRatingsLimitedByNumberOfMovies(Integer numberOfMovies) {
        return limitByNumberOfMovies(getAllSortByNumberOfRatings(), numberOfMovies);
    }

    public List<MovieResponse> sortByRating(List<Movie> movies) {
        return movies.stream()
                .map(Mapper::toMovieResponse)
                .sorted(Comparator.comparing(MovieResponse::getRating).reversed())
                .toList();
    }

    public List<MovieResponse> sortByReleaseYear(List<Movie> movies) {
        return movies.stream()
                .map(Mapper::toMovieResponse)
                .sorted(Comparator.comparing(MovieResponse::getReleaseYear).reversed())
                .toList();
    }

    public List<MovieResponse> sortByTitle(List<Movie> movies) {
        return movies.stream()
                .map(Mapper::toMovieResponse)
                .sorted(Comparator.comparing(MovieResponse::getTitle))
                .toList();
    }

    public List<MovieResponse> getAllSortedByReleaseYearLimitedByNumberOfMovies(Integer numberOfMovies) {
        List<Movie> movies = movieRepository.findAll();
        return limitByNumberOfMovies(sortByReleaseYear(movies), numberOfMovies);
    }

    public List<MovieResponse> getAllSortedByRatingLimitedByNumberOfMovies(Integer numberOfMovies) {
        List<Movie> movies = movieRepository.findAll();
        return limitByNumberOfMovies(sortByRating(movies), numberOfMovies);
    }

    public List<MovieResponse> limitByNumberOfMovies(List<MovieResponse> movieResponses, int numberOfMovies) {
        return movieResponses.subList(0, Math.min(movieResponses.size(), numberOfMovies));
    }

    public List<MovieResponse> getAll() {
        return movieRepository.findAll().stream()
                .map(Mapper::toMovieResponse)
                .toList();
    }

    public void updateById(Long movieId, MovieRequest movieRequest) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(String.valueOf(movieId)));
        movie.setTitle(movieRequest.getTitle());
        movie.setReleaseYear(movieRequest.getReleaseYear());
        movie.setDuration(movieRequest.getDuration());
        movie.setStoryline(movieRequest.getStoryline());
        movie.setCategories(movieRequest.getCategories());
        movieRepository.save(movie);
    }

    public void addCastMember(Long movieId, Long castId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(String.valueOf(movieId)));
        List<Long> casts = movie.getCasts();
        if (!casts.contains(castId)) {
            addMovieIdToCast(castId, movie);
            casts.add(castId);
            movie.setCasts(casts);
        }
        movieRepository.save(movie);
    }

    public void addCategory(Long movieId, Category category) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(String.valueOf(movieId)));
        List<Category> categories = movie.getCategories();
        if (!categories.contains(category)) {
            categories.add(category);
            movie.setCategories(categories);
        }
        movieRepository.save(movie);
    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    public void removeCastMember(Long movieId, Long castId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(String.valueOf(movieId)));
        List<Long> casts = movie.getCasts();
        if (casts.contains(castId)) {
            removeMovieIdFromCast(castId, movie);
            casts.remove(castId);
            movie.setCasts(casts);
        }
        movieRepository.save(movie);
    }

    /*public void removeCategory(Long movieId, Category category) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(String.valueOf(movieId)));
        List<Category> categories = movie.getCategories();
        if (categories.contains(category)) {
            categories.remove(category);
            movie.setCategories(categories);
        }
        movieRepository.save(movie);
    }*/

    public void rateMovie(Long movieId, RatingRequest ratingRequest) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(String.valueOf(movieId)));
        List<Rating> ratings = movie.getRatings();

        if (ratings.stream().noneMatch(rating -> rating.getUserId() == ratingRequest.getUserId())) {
            validateUserId(ratingRequest.getUserId());
            Rating rating = Mapper.toRating(ratingRequest);
            rating.setMovie(movie);
            ratings.add(rating);
            movie.setRatings(ratings);
            movieRepository.save(movie);
        } else {
            Rating rating = ratingRepository.findAllByMovieId(movieId).stream()
                    .filter(r -> r.getUserId() == ratingRequest.getUserId())
                    .findFirst()
                    .orElseThrow(RatingFailedException::new);
            rating.setValue(ratingRequest.getValue());
            ratingRepository.save(rating);
        }
    }

    public List<RatingResponse> getRatingsByUserId(Long userId) {
        return ratingRepository.findAllByUserId(userId)
                .stream()
                .map(Mapper::toRatingResponse)
                .toList();
    }

    /*public void removeRating(Long movieId, Long userId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(String.valueOf(movieId)));
        List<Rating> ratings = movie.getRatings();
        List<Rating> removedRatings = ratings.stream()
                .filter(rating -> rating.getUserId() == userId)
                .toList();
        if (!removedRatings.isEmpty()) {
            ratings.remove(removedRatings.get(0));
            movie.setRatings(ratings);
            movieRepository.save(movie);
        }
    }*/

    public Boolean validateMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(String.valueOf(movieId)));
        return true;
    }

    public void removeCastMemberFromAllMovies(Long castId) {
        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies) {
            removeCastMember(movie.getId(), castId);
        }
    }

    public void uploadPoster(MultipartFile imageFile, Long movieId) throws IOException {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException(String.valueOf(movieId)));
        movie.setImage(imageFile.getBytes());
        movieRepository.save(movie);
    }

    public void removeMovieIdFromCast(Long castId, Movie movie) {
        try {
            webClientBuilder
                    .baseUrl("http://localhost:8083/cast/admin/v1")
                    .build()
                    .put()
                    .uri("/remove-movie/castId=" + castId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Mapper.toMovieResponse(movie))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            throw new CastNotFoundException(String.valueOf(castId));
        }
    }

    public void validateUserId(Long userId) {
        try {
            webClientBuilder
                    .baseUrl("http://localhost:8080/user/v1")
                    .build()
                    .get()
                    .uri("/validate/userId=" + userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            throw new UserNotFoundException(String.valueOf(userId));
        }
    }

    public void addMovieIdToCast(Long castId, Movie movie) {
        try {
            webClientBuilder
                    .baseUrl("http://localhost:8083/cast/admin/v1")
                    .build()
                    .put()
                    .uri("/add-movie/castId=" + castId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Mapper.toMovieResponse(movie))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            throw new CastNotFoundException(String.valueOf(castId));
        }
    }
}

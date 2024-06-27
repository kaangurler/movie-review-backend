package com.example.movieservice.exception;

import com.example.movieservice.dto.ErrorResponse;
import com.example.movieservice.exception.custom.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.io.IOException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .messages(List.of(e.getMessage()))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<String> errorDetails = new ArrayList<>();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getAllErrors()
                .forEach(item -> errorDetails.add(item.getDefaultMessage()));
        ErrorResponse errorResponse = ErrorResponse.builder().
                messages(errorDetails)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .messages(List.of("A movie with the same title already exists"))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFound(MovieNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .messages(List.of("Movie ID#" + e.getMessage() + " cannot be found"))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CastNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCastNotFound(CastNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .messages(List.of("Cast ID#" + e.getMessage() + " cannot be found"))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .messages(List.of("User ID#" + e.getMessage() + " cannot be found"))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RatingFailedException.class)
    public ResponseEntity<ErrorResponse> handleRatingFailed(RatingFailedException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .messages(List.of("Rating attempt has failed"))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageUploadFailedException.class)
    public ResponseEntity<ErrorResponse> handleImageUploadFailed(ImageUploadFailedException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .messages(List.of("Image upload has failed"))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(InvalidFormatException e) {
        String[] splittedMessage = e.getOriginalMessage().split(" ");
        ErrorResponse errorResponse = ErrorResponse.builder()
                .messages(List.of("You provided: " + splittedMessage[8] +
                        " - Expected Type: " + e.getTargetType().getSimpleName()))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIO(IOException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .messages(List.of("Image upload has failed"))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

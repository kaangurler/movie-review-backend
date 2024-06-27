package com.example.reviewservice.exception;

import com.example.reviewservice.dto.ErrorResponse;
import com.example.reviewservice.exception.custom.MovieNotFoundException;
import com.example.reviewservice.exception.custom.ReviewNotFoundException;
import com.example.reviewservice.exception.custom.UserNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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
                .messages(List.of("A review with the same title already exists"))
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReviewNotFound(ReviewNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .messages(List.of("Review ID#" + e.getMessage() + " cannot be found"))
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

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFound(MovieNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .messages(List.of("Movie ID#" + e.getMessage() + " cannot be found"))
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
}


package com.example.movieservice.exception.custom;

public class CastNotFoundException extends RuntimeException{
    public CastNotFoundException(String message) {
        super(message);
    }
}

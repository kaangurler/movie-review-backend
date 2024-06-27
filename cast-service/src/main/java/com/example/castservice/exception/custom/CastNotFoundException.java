package com.example.castservice.exception.custom;

public class CastNotFoundException extends RuntimeException{
    public CastNotFoundException(String message) {
        super(message);
    }
}

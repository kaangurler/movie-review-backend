package com.example.userservice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("Admin"),
    MEMBER("Member");

    private final String title;
}

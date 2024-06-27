package com.example.movieservice.entity;

import lombok.*;

@RequiredArgsConstructor
@Getter
public enum Category {
    ACTION("Action"),
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    BIOGRAPHY("Biography"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    DOCUMENTARY("Documentary"),
    FAMILY("Family"),
    FANTASY("Fantasy"),
    HISTORY("History"),
    HORROR("Horror"),
    MUSICAL("Musical"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    SCIFI("SciFi"),
    THRILLER("Thriller");

    private final String title;
}

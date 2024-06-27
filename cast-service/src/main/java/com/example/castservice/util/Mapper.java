package com.example.castservice.util;

import com.example.castservice.dto.CastRequest;
import com.example.castservice.dto.CastResponse;
import com.example.castservice.entity.Cast;

public class Mapper {
    public static CastResponse toCastResponse(Cast cast) {
        return CastResponse.builder()
                .id(cast.getId())
                .name(cast.getName())
                .surname(cast.getSurname())
                .age(cast.getAge())
                .movies(cast.getMovies())
                .photo(cast.getPhoto())
                .build();
    }

    public static Cast toCast(CastRequest castRequest) {
        return Cast.builder()
                .name(castRequest.getName())
                .surname(castRequest.getSurname())
                .age(castRequest.getAge())
                .build();
    }
}

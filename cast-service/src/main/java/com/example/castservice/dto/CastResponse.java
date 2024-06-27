package com.example.castservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CastResponse {
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private List<Long> movies;
    private byte[] photo;
}

package com.example.castservice.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CastRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    private String surname;
    @NotNull(message = "Age cannot be empty")
    @DecimalMin(value = "0", message = "Age cannot be less than 0")
    @DecimalMax(value = "150", message = "Age cannot be greater than 150")
    private Integer age;
}

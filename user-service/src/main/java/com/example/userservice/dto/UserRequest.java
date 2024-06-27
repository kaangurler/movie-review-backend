package com.example.userservice.dto;

import com.example.userservice.entity.Role;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Surname cannot be empty")
    private String surname;
    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotNull(message = "Age cannot be empty")
    @DecimalMin(value = "1", message = "Age cannot be less than 1")
    @DecimalMax(value = "150", message = "Age cannot be greater than 150")
    private Integer age;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 18, message = "Password length must be between 6-18")
    private String password;
    @NotNull(message = "Role cannot be empty")
    private Role role;
}

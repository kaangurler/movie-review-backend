package com.example.userservice.dto;

import com.example.userservice.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private Integer age;
    private Role role;
}

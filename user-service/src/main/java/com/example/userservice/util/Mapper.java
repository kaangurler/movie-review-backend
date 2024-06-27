package com.example.userservice.util;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entity.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class Mapper {

    public static UserResponse toUserResponse(AppUser appUser) {
        return UserResponse.builder()
                .id(appUser.getId())
                .name(appUser.getName())
                .surname(appUser.getSurname())
                .username(appUser.getUsername())
                .age(appUser.getAge())
                .role(appUser.getRole())
                .build();
    }

    public static AppUser toAppUser(UserRequest userRequest, String encodedPassword) {
        return AppUser.builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .username(userRequest.getUsername())
                .age(userRequest.getAge())
                .password(encodedPassword)
                .role(userRequest.getRole())
                .build();
    }
}

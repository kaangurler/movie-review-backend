package com.example.userservice.controller;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/id={id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/username={username}")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/validate/userId={userId}")
    public ResponseEntity<Boolean> validateUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.validateUser(userId));
    }

    @PutMapping("/id={id}")
    public void update(@RequestBody UserRequest userRequest, @PathVariable Long id) {
        userService.update(userRequest, id);
    }

    @DeleteMapping("/id={id}")
    public void deleteProfile(@PathVariable Long id) {
        userService.deleteById(id);
    }
}

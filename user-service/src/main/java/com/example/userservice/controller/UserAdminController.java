package com.example.userservice.controller;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/admin/v1")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserService userService;

    @DeleteMapping("/id={id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}

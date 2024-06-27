package com.example.userservice.service;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entity.AppUser;
import com.example.userservice.exception.custom.UserNotFoundException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getById(Long id) {
        return Mapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id))));
    }

    public UserResponse getByUsername(String username) {
        return Mapper.toUserResponse(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
    }

    public List<UserResponse> getAll() {
        return userRepository.findAll()
                .stream()
                .map(Mapper::toUserResponse)
                .toList();
    }

    public Boolean validateUser(Long userId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        return true;
    }

    public void update(UserRequest userRequest, Long userId) {
        System.out.println("Updating User");
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setAge(userRequest.getAge());
        if (!userRequest.getPassword().isEmpty()) {
            System.out.println("Changed Password");
            user.setPassword(userRequest.getPassword());
        }
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

package com.example.userservice.service;

import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.TokenDto;
import com.example.userservice.dto.UserRequest;
import com.example.userservice.entity.AppUser;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public void register(UserRequest userRequest) {
        userRepository.save(Mapper.toAppUser(userRequest, passwordEncoder.encode(userRequest.getPassword())));
    }

    public TokenDto login(LoginRequest loginRequest) {
        AppUser user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(loginRequest.getUsername()));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(jwtService.generateToken(user));
        return tokenDto;
    }
}

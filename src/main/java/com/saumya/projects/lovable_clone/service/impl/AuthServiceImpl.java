package com.saumya.projects.lovable_clone.service.impl;

import com.saumya.projects.lovable_clone.dto.auth.AuthResponse;
import com.saumya.projects.lovable_clone.dto.auth.LoginRequest;
import com.saumya.projects.lovable_clone.dto.auth.SignupRequest;
import com.saumya.projects.lovable_clone.entity.User;
import com.saumya.projects.lovable_clone.exceptions.BadRequestException;
import com.saumya.projects.lovable_clone.mapper.UserMapper;
import com.saumya.projects.lovable_clone.repository.UserRepository;
import com.saumya.projects.lovable_clone.security.AuthUtil;
import com.saumya.projects.lovable_clone.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    AuthUtil authUtil;
    AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(SignupRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(user -> {
            throw new BadRequestException("User already exists " + request.username());
        });

        User user = userMapper.toEntity(request);
        // this stores password as normal String, use Spring Security
        user.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(user);

        String authToken = authUtil.generateToken(user);

        return new AuthResponse(authToken, userMapper.toProfileResponse(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()) // validates credentials
        );

        User user = (User) authentication.getPrincipal(); // extracts the authenticated user (the principal) from authentication
        String token = null;
        if (user != null) {
            token = authUtil.generateToken(user);
        }

        return new AuthResponse(token, userMapper.toProfileResponse(user));
    }
}

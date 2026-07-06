package com.saumya.projects.lovable_clone.controller;

import com.saumya.projects.lovable_clone.dto.auth.AuthResponse;
import com.saumya.projects.lovable_clone.dto.auth.LoginRequest;
import com.saumya.projects.lovable_clone.dto.auth.SignupRequest;
import com.saumya.projects.lovable_clone.dto.auth.UserProfileResponse;
import com.saumya.projects.lovable_clone.service.AuthService;
import com.saumya.projects.lovable_clone.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody @Valid SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getProfile() {
        Long userId = 1L;   // hardcoded for now
        return ResponseEntity.ok(userService.getProfile(userId));
    }
}

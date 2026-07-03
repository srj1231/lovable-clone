package com.saumya.projects.lovable_clone.service;

import com.saumya.projects.lovable_clone.dto.auth.AuthResponse;
import com.saumya.projects.lovable_clone.dto.auth.LoginRequest;
import com.saumya.projects.lovable_clone.dto.auth.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}

package com.aasoo.jwt_auth_in_memory_user.service;

import com.aasoo.jwt_auth_in_memory_user.dao.request.AuthRequest;
import com.aasoo.jwt_auth_in_memory_user.dao.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<AuthResponse> login(AuthRequest authRequest);
}

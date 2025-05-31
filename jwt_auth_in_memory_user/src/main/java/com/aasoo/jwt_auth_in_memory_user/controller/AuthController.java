package com.aasoo.jwt_auth_in_memory_user.controller;

import com.aasoo.jwt_auth_in_memory_user.dao.request.AuthRequest;
import com.aasoo.jwt_auth_in_memory_user.dao.response.AuthResponse;
import com.aasoo.jwt_auth_in_memory_user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

}

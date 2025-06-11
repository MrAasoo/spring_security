package com.aasoo.oauth2_jwt_db_user.controller;

import com.aasoo.oauth2_jwt_db_user.dao.request.AuthRequest;
import com.aasoo.oauth2_jwt_db_user.dao.response.AuthResponse;
import com.aasoo.oauth2_jwt_db_user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @GetMapping("/google/callback")
    public ResponseEntity<?> googleCallback(@RequestParam String code) {
        return authService.googleAuth(code);
    }


    @GetMapping("/github/callback")
    public ResponseEntity<?> githubCallback(@RequestParam String code) {
        return authService.githubAuth(code);
    }
}

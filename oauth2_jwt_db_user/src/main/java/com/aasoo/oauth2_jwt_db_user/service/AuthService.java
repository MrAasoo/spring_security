package com.aasoo.oauth2_jwt_db_user.service;

import com.aasoo.oauth2_jwt_db_user.dao.request.AuthRequest;
import com.aasoo.oauth2_jwt_db_user.dao.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<AuthResponse> login(AuthRequest authRequest);

    ResponseEntity<?> googleAuth(String code);

    ResponseEntity<?> githubAuth(String code);
}

package com.aasoo.jwt_auth_db_user.service;

import com.aasoo.jwt_auth_db_user.dao.request.AuthRequest;
import com.aasoo.jwt_auth_db_user.dao.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<?> addUser(AuthRequest authRequest, String role);

    ResponseEntity<?> addUser(AuthRequest authRequest);
}

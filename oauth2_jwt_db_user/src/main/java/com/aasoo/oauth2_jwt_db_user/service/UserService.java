package com.aasoo.oauth2_jwt_db_user.service;

import com.aasoo.oauth2_jwt_db_user.dao.request.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<?> addUser(AuthRequest authRequest, String role);

    ResponseEntity<?> addUser(AuthRequest authRequest);
}

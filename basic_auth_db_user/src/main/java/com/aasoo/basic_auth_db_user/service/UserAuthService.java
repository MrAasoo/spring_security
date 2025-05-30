package com.aasoo.basic_auth_db_user.service;

import com.aasoo.basic_auth_db_user.dao.request.AddUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserAuthService {
    ResponseEntity<?> addUser(AddUser addUser);

    ResponseEntity<?> addAdmin(AddUser addUser, String role);

    ResponseEntity<?> getUserDetails(String username);
}

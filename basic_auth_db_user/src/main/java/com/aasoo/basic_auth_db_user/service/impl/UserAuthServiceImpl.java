package com.aasoo.basic_auth_db_user.service.impl;

import com.aasoo.basic_auth_db_user.dao.request.AddUser;
import com.aasoo.basic_auth_db_user.dao.response.UserLogin;
import com.aasoo.basic_auth_db_user.entity.UserAuth;
import com.aasoo.basic_auth_db_user.repo.UserAuthRepository;
import com.aasoo.basic_auth_db_user.service.UserAuthService;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> addUser(AddUser addUser) {
        if (Objects.nonNull(addUser) && Objects.nonNull(addUser.getUsername()) && Objects.nonNull(addUser.getPassword())) {
            Optional<UserAuth> userAuthOptional = userAuthRepository.findByUserName(addUser.getUsername());
            if (userAuthOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + addUser.getUsername() + " already exists");
            } else {
                UserAuth userAuth = saveUser(
                        addUser.getUsername(),
                        passwordEncoder.encode(addUser.getPassword()),
                        "USER"
                );
                UserLogin userLogin = new UserLogin(userAuth.getUserName(), userAuth.getRole());
                return ResponseEntity.ok(userLogin);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AddUser or username cannot be null");
        }

    }

    @Override
    public ResponseEntity<?> addAdmin(AddUser addUser, String role) {
        if (Objects.nonNull(addUser) && Objects.nonNull(addUser.getUsername()) && Objects.nonNull(addUser.getPassword()) && Objects.nonNull(role)) {
            Optional<UserAuth> userAuthOptional = userAuthRepository.findByUserName(addUser.getUsername());
            if (userAuthOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + addUser.getUsername() + " already exists");
            } else {
                UserAuth userAuth = saveUser(
                        addUser.getUsername(),
                        passwordEncoder.encode(addUser.getPassword()),
                        role
                );
                UserLogin userLogin = new UserLogin(userAuth.getUserName(), userAuth.getRole());
                return ResponseEntity.ok(userLogin);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AddUser or username cannot be null");
        }

    }

    @Override
    public ResponseEntity<?> getUserDetails(String username) {
        if (Objects.nonNull(username) && !username.trim().isEmpty()) {
            Optional<UserAuth> userAuthOptional = userAuthRepository.findByUserName(username);
            if (userAuthOptional.isPresent()) {
                UserAuth userAuth = userAuthOptional.get();
                UserLogin userLogin = new UserLogin(userAuth.getUserName(), userAuth.getRole());
                return ResponseEntity.ok(userLogin);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + username + " not found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid username!");
        }
    }

    public UserAuth saveUser(String username, String password, String role) {
        UserAuth userAuth = new UserAuth(username, password, role);
        userAuth = userAuthRepository.save(userAuth);
        return userAuth;
    }
}

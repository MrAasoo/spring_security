package com.aasoo.jwt_auth_db_user.service.impl;

import com.aasoo.jwt_auth_db_user.dao.request.AuthRequest;
import com.aasoo.jwt_auth_db_user.dao.response.AuthResponse;
import com.aasoo.jwt_auth_db_user.dao.response.UserRes;
import com.aasoo.jwt_auth_db_user.entity.Role;
import com.aasoo.jwt_auth_db_user.entity.UserAuth;
import com.aasoo.jwt_auth_db_user.repo.UserAuthRepository;
import com.aasoo.jwt_auth_db_user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.username(), authRequest.password());
        authManager.authenticate(authentication);

        UserDetails user = userDetailsService.loadUserByUsername(authRequest.username());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(user.getUsername(), token));
    }

}

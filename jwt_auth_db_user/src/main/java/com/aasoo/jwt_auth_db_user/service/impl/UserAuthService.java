package com.aasoo.jwt_auth_db_user.service.impl;

import com.aasoo.jwt_auth_db_user.dao.request.AuthRequest;
import com.aasoo.jwt_auth_db_user.dao.response.AuthResponse;
import com.aasoo.jwt_auth_db_user.entity.UserAuth;
import com.aasoo.jwt_auth_db_user.repo.UserAuthRepository;
import com.aasoo.jwt_auth_db_user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByUsername(username);
        if (userAuthOptional.isPresent()) {
            UserAuth userAuth = userAuthOptional.get();
            User user = new User(
                    userAuth.getUsername(),
                    userAuth.getPassword(),
                    userAuth.getRoles().stream().map(
                            role -> new SimpleGrantedAuthority("ROLE_" + role.getName())
                    ).toList()
            );
            return user;
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}

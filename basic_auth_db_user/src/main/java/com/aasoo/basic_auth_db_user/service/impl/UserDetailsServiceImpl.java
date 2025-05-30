package com.aasoo.basic_auth_db_user.service.impl;

import com.aasoo.basic_auth_db_user.dao.request.AddUser;
import com.aasoo.basic_auth_db_user.dao.response.UserLogin;
import com.aasoo.basic_auth_db_user.entity.UserAuth;
import com.aasoo.basic_auth_db_user.repo.UserAuthRepository;
import com.aasoo.basic_auth_db_user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAuth> userAuthOptional = userAuthRepository.findByUserName(username);
        if (userAuthOptional.isPresent()) {
            UserAuth userAuth = userAuthOptional.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(userAuth.getUserName())
                    .password(userAuth.getPassword())
                    .roles(userAuth.getRole())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}

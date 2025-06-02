package com.aasoo.jwt_auth_db_user.service.impl;

import com.aasoo.jwt_auth_db_user.dao.request.AuthRequest;
import com.aasoo.jwt_auth_db_user.dao.response.AuthResponse;
import com.aasoo.jwt_auth_db_user.dao.response.UserRes;
import com.aasoo.jwt_auth_db_user.entity.Role;
import com.aasoo.jwt_auth_db_user.entity.UserAuth;
import com.aasoo.jwt_auth_db_user.repo.RoleRepository;
import com.aasoo.jwt_auth_db_user.repo.UserAuthRepository;
import com.aasoo.jwt_auth_db_user.service.AuthService;
import com.aasoo.jwt_auth_db_user.service.UserService;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserAuthRepository userAuthRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> addUser(AuthRequest authRequest, String roleName) {
        if (Objects.nonNull(authRequest) && Objects.nonNull(authRequest.username()) && Objects.nonNull(authRequest.password()) && Objects.nonNull(roleName)) {
            Optional<UserAuth> userAuthOptional = userAuthRepository.findByUsername(authRequest.username());
            Optional<Role> roleOptional = roleRepository.findByName(roleName);
            Role role = new Role();
            if (roleOptional.isEmpty()) {
                role.setName(roleName);
                role = roleRepository.save(role);
            } else {
                role = roleOptional.get();
            }

            if (userAuthOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + authRequest.username() + " already exists");
            } else {
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                UserAuth userAuth = saveUser(
                        authRequest.username(),
                        passwordEncoder.encode(authRequest.password()),
                        roles
                );
                UserRes userLogin = new UserRes(userAuth.getUsername(), userAuth.getRoles());
                return ResponseEntity.ok(userLogin);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("AddUser or username cannot be null");
        }
    }

    @Override
    public ResponseEntity<?> addUser(AuthRequest authRequest) {
        return addUser(authRequest, "USER");
    }

    public UserAuth saveUser(String username, String password, Set<Role> roles) {
        UserAuth userAuth = new UserAuth(username, password, roles);
        userAuth = userAuthRepository.save(userAuth);
        return userAuth;
    }
}

package com.aasoo.jwt_auth_db_user.service;

import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    boolean existsByName(String name);

    String saveRole(String name);
    String saveRole(String... names);
}

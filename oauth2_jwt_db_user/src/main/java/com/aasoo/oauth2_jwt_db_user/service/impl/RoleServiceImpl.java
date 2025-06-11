package com.aasoo.oauth2_jwt_db_user.service.impl;

import com.aasoo.oauth2_jwt_db_user.entity.Role;
import com.aasoo.oauth2_jwt_db_user.repo.RoleRepository;
import com.aasoo.oauth2_jwt_db_user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name.trim().toUpperCase());
    }

    @Override
    public String saveRole(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }
        String validRole = name.trim().toUpperCase();
        if (existsByName(validRole)) {
            return "Role already exists";
        }
        Role role = new Role();
        role.setName(validRole);
        roleRepository.save(role);
        return "Role saved successfully";
    }

    @Override
    public String saveRole(String... names) {
        if (names == null || names.length == 0) {
            throw new IllegalArgumentException("Role names cannot be null or empty");
        }

        StringBuilder result = new StringBuilder();

        for (String name : names) {
            if (name == null || name.trim().isEmpty()) {
                result.append("Skipped empty or null role name.\n");
                continue;
            }

            String validRole = name.trim().toUpperCase();

            if (existsByName(validRole)) {
                result.append("Role '").append(validRole).append("' already exists.\n");
            } else {
                Role role = new Role();
                role.setName(validRole);
                roleRepository.save(role);
                result.append("Role '").append(validRole).append("' saved successfully.\n");
            }
        }

        return result.toString().trim();
    }
}

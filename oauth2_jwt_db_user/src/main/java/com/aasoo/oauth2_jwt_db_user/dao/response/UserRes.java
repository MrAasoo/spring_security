package com.aasoo.oauth2_jwt_db_user.dao.response;

import com.aasoo.oauth2_jwt_db_user.entity.Role;

import java.util.Set;

public record UserRes(String username, Set<Role> roles) {
}

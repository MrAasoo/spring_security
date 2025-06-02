package com.aasoo.jwt_auth_db_user.dao.request;

public record AuthRequest(
        String username,
        String password
) {
}

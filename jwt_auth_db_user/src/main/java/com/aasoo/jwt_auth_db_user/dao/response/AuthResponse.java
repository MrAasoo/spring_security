package com.aasoo.jwt_auth_db_user.dao.response;

public record AuthResponse(
        String username,
        String token
) {
}

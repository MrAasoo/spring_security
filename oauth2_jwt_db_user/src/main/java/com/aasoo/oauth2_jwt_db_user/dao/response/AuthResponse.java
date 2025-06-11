package com.aasoo.oauth2_jwt_db_user.dao.response;

public record AuthResponse(
        String username,
        String token
) {
}

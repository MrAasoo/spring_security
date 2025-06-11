package com.aasoo.oauth2_jwt_db_user.dao.request;

public record AuthRequest(
        String username,
        String password
) {
}

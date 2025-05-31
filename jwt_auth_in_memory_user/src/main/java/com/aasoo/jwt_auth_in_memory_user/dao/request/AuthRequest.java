package com.aasoo.jwt_auth_in_memory_user.dao.request;

public record AuthRequest(
        String username,
        String password
) {
}

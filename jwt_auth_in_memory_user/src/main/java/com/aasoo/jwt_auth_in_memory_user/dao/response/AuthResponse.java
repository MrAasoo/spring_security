package com.aasoo.jwt_auth_in_memory_user.dao.response;

public record AuthResponse(
        String username,
        String token
) {
}

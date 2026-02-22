package com.bookstore.dto.response;

import java.util.List;

public record JwtResponseDto(
        String token,
        String tokenType,
        long expiresIn,
        String username,
        String firstname,
        List<String> roles
) {
    public JwtResponseDto(String token, long expiresIn, String username, String firstname, List<String> roles) {
        this(token, "Bearer", expiresIn, username, firstname, roles);
    }
}

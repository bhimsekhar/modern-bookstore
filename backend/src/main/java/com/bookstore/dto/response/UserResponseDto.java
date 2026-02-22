package com.bookstore.dto.response;

public record UserResponseDto(
        String username,
        String firstname,
        String lastname,
        String email
) {}

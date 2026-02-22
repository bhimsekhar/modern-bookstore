package com.bookstore.dto.request;

import jakarta.validation.constraints.*;

public record RegisterRequestDto(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, digits, and underscores")
        String username,

        @NotBlank(message = "Password is required")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
                 message = "Password must be at least 8 characters with at least one letter and one digit")
        String password,

        @NotBlank(message = "First name is required")
        @Size(max = 100, message = "First name must not exceed 100 characters")
        String firstname,

        @NotBlank(message = "Last name is required")
        @Size(max = 100, message = "Last name must not exceed 100 characters")
        String lastname,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email address")
        String email,

        @Size(max = 500, message = "Address must not exceed 500 characters")
        String address,

        @Pattern(regexp = "\\d{0,20}", message = "Phone must contain digits only")
        String phone
) {}

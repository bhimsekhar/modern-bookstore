package com.bookstore.dto.request;

import jakarta.validation.constraints.*;

public record FeedbackRequestDto(
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must not exceed 255 characters")
        String name,

        @Pattern(regexp = "^[+\\d\\s\\-]{7,20}$", message = "Invalid phone number format")
        String phone,

        @Email(message = "Invalid email address")
        String email,

        @NotBlank(message = "Feedback message is required")
        @Size(min = 10, max = 2000, message = "Feedback must be between 10 and 2000 characters")
        String feedback
) {}

package com.bookstore.dto.response;

import java.time.LocalDateTime;

public record FeedbackResponseDto(
        Long id,
        String name,
        String phone,
        String email,
        String feedback,
        LocalDateTime dateCreated
) {}

package com.bookstore.dto.response;

import java.time.LocalDateTime;

public record PurchaseResponseDto(
        Long id,
        String name,
        String phone,
        String books,
        Integer quantity,
        Double totalPrice,
        LocalDateTime datePurchased
) {}

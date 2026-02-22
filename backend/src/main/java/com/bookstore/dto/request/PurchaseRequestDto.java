package com.bookstore.dto.request;

import jakarta.validation.constraints.*;

public record PurchaseRequestDto(
        @NotBlank(message = "Buyer name is required")
        @Size(max = 255, message = "Name must not exceed 255 characters")
        String name,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[+\\d\\s\\-]{7,20}$", message = "Invalid phone number format")
        String phone,

        @NotBlank(message = "Books field is required")
        @Size(max = 500, message = "Books field must not exceed 500 characters")
        String books,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity,

        @NotNull(message = "Total price is required")
        @DecimalMin(value = "0.01", message = "Total price must be positive")
        Double totalPrice
) {}

package com.bookstore.dto.request;

import jakarta.validation.constraints.*;

public record BookRequestDto(
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @NotBlank(message = "Author is required")
        @Size(max = 255, message = "Author must not exceed 255 characters")
        String author,

        @NotBlank(message = "Genre is required")
        @Size(max = 100, message = "Genre must not exceed 100 characters")
        String genre,

        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        String description,

        @NotNull(message = "Copy count is required")
        @Min(value = 0, message = "Copy count cannot be negative")
        Integer copy,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        Double price
) {}

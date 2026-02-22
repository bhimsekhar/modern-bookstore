package com.bookstore.dto.response;

public record BookResponseDto(
        Long id,
        String title,
        String author,
        String genre,
        String description,
        Integer copy,
        Double price
) {}

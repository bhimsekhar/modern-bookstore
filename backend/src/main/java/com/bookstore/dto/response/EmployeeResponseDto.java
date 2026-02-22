package com.bookstore.dto.response;

public record EmployeeResponseDto(
        Long id,
        String name,
        Double salary,
        String department
) {}

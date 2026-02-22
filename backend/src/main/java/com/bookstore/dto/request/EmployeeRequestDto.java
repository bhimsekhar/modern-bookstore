package com.bookstore.dto.request;

import jakarta.validation.constraints.*;

public record EmployeeRequestDto(
        @NotBlank(message = "Employee name is required")
        @Size(max = 255, message = "Name must not exceed 255 characters")
        String name,

        @NotNull(message = "Salary is required")
        @DecimalMin(value = "0.01", message = "Salary must be greater than 0")
        Double salary,

        @NotBlank(message = "Department is required")
        @Size(max = 100, message = "Department must not exceed 100 characters")
        String department
) {}

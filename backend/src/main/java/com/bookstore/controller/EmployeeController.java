package com.bookstore.controller;

import com.bookstore.dto.request.EmployeeRequestDto;
import com.bookstore.dto.response.ApiResponse;
import com.bookstore.dto.response.EmployeeResponseDto;
import com.bookstore.dto.response.PagedResponse;
import com.bookstore.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ApiResponse<PagedResponse<EmployeeResponseDto>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "") String search) {
        return ApiResponse.success(employeeService.getAllEmployees(page, size, sortField, sortDir, search));
    }

    @GetMapping("/{id}")
    public ApiResponse<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        return ApiResponse.success(employeeService.getEmployeeById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto dto) {
        return ApiResponse.success(employeeService.createEmployee(dto), "Employee created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<EmployeeResponseDto> updateEmployee(@PathVariable Long id,
                                                            @Valid @RequestBody EmployeeRequestDto dto) {
        return ApiResponse.success(employeeService.updateEmployee(id, dto), "Employee updated successfully");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}

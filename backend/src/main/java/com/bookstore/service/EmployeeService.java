package com.bookstore.service;

import com.bookstore.dto.request.EmployeeRequestDto;
import com.bookstore.dto.response.EmployeeResponseDto;
import com.bookstore.dto.response.PagedResponse;
import com.bookstore.entity.Employee;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public PagedResponse<EmployeeResponseDto> getAllEmployees(int page, int size, String sortField, String sortDir, String search) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> result;
        if (StringUtils.hasText(search)) {
            result = employeeRepository.findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
                    search, search, pageable);
        } else {
            result = employeeRepository.findAll(pageable);
        }

        return toPagedResponse(result);
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return toDto(emp);
    }

    public EmployeeResponseDto createEmployee(EmployeeRequestDto dto) {
        Employee emp = Employee.builder()
                .name(dto.name())
                .salary(dto.salary())
                .department(dto.department())
                .build();
        return toDto(employeeRepository.save(emp));
    }

    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto dto) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        emp.setName(dto.name());
        emp.setSalary(dto.salary());
        emp.setDepartment(dto.department());

        return toDto(employeeRepository.save(emp));
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
        employeeRepository.deleteById(id);
    }

    private EmployeeResponseDto toDto(Employee e) {
        return new EmployeeResponseDto(e.getId(), e.getName(), e.getSalary(), e.getDepartment());
    }

    private PagedResponse<EmployeeResponseDto> toPagedResponse(Page<Employee> page) {
        return new PagedResponse<>(
                page.getContent().stream().map(this::toDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}

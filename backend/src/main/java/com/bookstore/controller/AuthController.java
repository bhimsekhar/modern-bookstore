package com.bookstore.controller;

import com.bookstore.dto.request.LoginRequestDto;
import com.bookstore.dto.request.RegisterRequestDto;
import com.bookstore.dto.response.ApiResponse;
import com.bookstore.dto.response.JwtResponseDto;
import com.bookstore.dto.response.UserResponseDto;
import com.bookstore.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) {
        UserResponseDto user = authService.register(dto);
        return ApiResponse.success(user, "Registration successful");
    }

    @PostMapping("/login")
    public ApiResponse<JwtResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        JwtResponseDto response = authService.login(dto);
        return ApiResponse.success(response);
    }
}

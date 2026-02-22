package com.bookstore.service;

import com.bookstore.dto.request.LoginRequestDto;
import com.bookstore.dto.request.RegisterRequestDto;
import com.bookstore.dto.response.JwtResponseDto;
import com.bookstore.dto.response.UserResponseDto;
import com.bookstore.entity.Role;
import com.bookstore.entity.User;
import com.bookstore.exception.DuplicateResourceException;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserResponseDto register(RegisterRequestDto dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new DuplicateResourceException("Username '" + dto.username() + "' is already taken");
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new DuplicateResourceException("Email '" + dto.email() + "' is already registered");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));

        User user = User.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .firstname(dto.firstname())
                .lastname(dto.lastname())
                .email(dto.email())
                .address(dto.address())
                .phone(dto.phone())
                .roles(Set.of(userRole))
                .build();

        User saved = userRepository.save(user);
        return new UserResponseDto(saved.getUsername(), saved.getFirstname(), saved.getLastname(), saved.getEmail());
    }

    @Transactional(readOnly = true)
    public JwtResponseDto login(LoginRequestDto dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));

        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new JwtResponseDto(token, 86400L, user.getUsername(), user.getFirstname(), roles);
    }
}

package com.bookstore.config;

import com.bookstore.entity.Role;
import com.bookstore.entity.User;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Seed roles
        Role adminRole = seedRole("ROLE_ADMIN");
        Role employeeRole = seedRole("ROLE_EMPLOYEE");
        seedRole("ROLE_USER");

        // Seed default admin user
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("Admin123"))
                    .firstname("System")
                    .lastname("Admin")
                    .password(passwordEncoder.encode(System.getenv("ADMIN_PASSWORD")))
                    .roles(Set.of(adminRole))
                    .build();
            userRepository.save(admin);
            log.info("Default admin user created: username=admin, password=Admin123");
        }

        // Seed default employee user
        if (!userRepository.existsByUsername("employee1")) {
            User emp = User.builder()
                    .username("employee1")
                    .password(passwordEncoder.encode("Employee123"))
                    .firstname("Jane")
                    .password(passwordEncoder.encode(System.getenv("EMPLOYEE_PASSWORD")))
                    .email("employee1@bookstore.com")
                    .roles(Set.of(employeeRole))
                    .build();
            userRepository.save(emp);
            log.info("Default employee user created: username=employee1, password=Employee123");
        }
    }

    private Role seedRole(String name) {
        return roleRepository.findByName(name).orElseGet(() -> {
            Role role = Role.builder().name(name).build();
            return roleRepository.save(role);
        });
    }
}

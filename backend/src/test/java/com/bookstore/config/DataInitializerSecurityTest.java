```java
import com.bookstore.config.DataInitializer;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataInitializerTest {

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void testVulnerabilityExisted() {
        // Given
        System.setProperty("admin.password", null);
        System.setProperty("employee.password", null);

        // When
        dataInitializer.init();

        // Then
        User admin = userRepository.findByUsername("admin").orElseThrow();
        assertTrue(passwordEncoder.matches("Admin123", admin.getPassword()));

        User employee = userRepository.findByUsername("employee1").orElseThrow();
        assertTrue(passwordEncoder.matches("Employee123", employee.getPassword()));
    }

    @Test
    void testFixPreventsExploitation() {
        // Given
        System.setProperty("admin.password", "CustomAdminPassword");
        System.setProperty("employee.password", "CustomEmployeePassword");

        // When
        dataInitializer.init();

        // Then
        User admin = userRepository.findByUsername("admin").orElseThrow();
        assertFalse(passwordEncoder.matches("Admin123", admin.getPassword()));
        assertTrue(passwordEncoder.matches("CustomAdminPassword", admin.getPassword()));

        User employee = userRepository.findByUsername("employee1").orElseThrow();
        assertFalse(passwordEncoder.matches("Employee123", employee.getPassword()));
        assertTrue(passwordEncoder.matches("CustomEmployeePassword", employee.getPassword()));
    }

    @Test
    void testNormalFunctionality() {
        // Given
        System.setProperty("admin.password", null);
        System.setProperty("employee.password", null);

        // When
        dataInitializer.init();

        // Then
        User admin = userRepository.findByUsername("admin").orElseThrow();
        assertNotNull(admin);
        assertEquals("admin", admin.getUsername());
        assertEquals("System", admin.getFirstname());
        assertEquals("Admin", admin.getLastname());

        User employee = userRepository.findByUsername("employee1").orElseThrow();
        assertNotNull(employee);
        assertEquals("employee1", employee.getUsername());
        assertEquals("Jane", employee.getFirstname());
        assertEquals("Smith", employee.getLastname());
    }
}
```
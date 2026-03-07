```java
import com.bookstore.config.DataInitializer;
import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
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
    void setUp() {
        // Remove any existing users
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        // Remove any existing users
        userRepository.deleteAll();
    }

    @Test
    void testVulnerabilityExisted() {
        // Simulate the vulnerability by setting the admin password to a hardcoded value
        System.setProperty("ADMIN_PASSWORD", "Admin123");
        dataInitializer.init();

        // Check if the admin user was created with the hardcoded password
        User admin = userRepository.findByUsername("admin");
        assertNotNull(admin);
        assertTrue(passwordEncoder.matches("Admin123", admin.getPassword()));
    }

    @Test
    void testFixPreventsExploitation() {
        // Set the admin password to a secure value
        System.setProperty("ADMIN_PASSWORD", "SecureAdminPassword123");
        dataInitializer.init();

        // Check if the admin user was created with the secure password
        User admin = userRepository.findByUsername("admin");
        assertNotNull(admin);
        assertFalse(passwordEncoder.matches("Admin123", admin.getPassword()));
        assertTrue(passwordEncoder.matches(System.getenv("ADMIN_PASSWORD"), admin.getPassword()));
    }

    @Test
    void testNormalFunctionality() {
        // Set the admin password to a secure value
        System.setProperty("ADMIN_PASSWORD", "SecureAdminPassword123");
        System.setProperty("EMPLOYEE_PASSWORD", "SecureEmployeePassword123");
        dataInitializer.init();

        // Check if the admin and employee users were created with the correct details
        User admin = userRepository.findByUsername("admin");
        assertNotNull(admin);
        assertEquals("admin", admin.getUsername());
        assertEquals("System", admin.getFirstname());
        assertEquals("Admin", admin.getLastname());
        assertTrue(passwordEncoder.matches(System.getenv("ADMIN_PASSWORD"), admin.getPassword()));

        User employee = userRepository.findByUsername("employee1");
        assertNotNull(employee);
        assertEquals("employee1", employee.getUsername());
        assertEquals("Jane", employee.getFirstname());
        assertEquals("Smith", employee.getLastname());
        assertTrue(passwordEncoder.matches(System.getenv("EMPLOYEE_PASSWORD"), employee.getPassword()));
    }
}
```
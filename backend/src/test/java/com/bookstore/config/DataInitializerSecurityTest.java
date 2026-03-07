```java
import com.bookstore.config.DataInitializer;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataInitializerTest {

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Clear the user repository before each test
        userRepository.deleteAll();
    }

    @Test
    void testVulnerabilityExisted() {
        // Simulate the old code with hardcoded credentials
        System.setProperty("ADMIN_PASSWORD", "Admin123");
        dataInitializer.initialize();
        User admin = userRepository.findByUsername("admin");
        assertNotNull(admin);
        // Attempt to login with hardcoded credentials
        assertTrue(admin.getPassword().equals("Admin123"));
    }

    @Test
    void testFixPreventsExploitation() {
        // Attempt to login with hardcoded credentials
        System.setProperty("ADMIN_PASSWORD", "SecurePassword");
        dataInitializer.initialize();
        User admin = userRepository.findByUsername("admin");
        assertNotNull(admin);
        // Verify that the password is not the hardcoded one
        assertNotEquals("Admin123", admin.getPassword());
    }

    @Test
    void testNormalFunctionality() {
        // Set the admin password as an environment variable
        System.setProperty("ADMIN_PASSWORD", "SecurePassword");
        dataInitializer.initialize();
        User admin = userRepository.findByUsername("admin");
        assertNotNull(admin);
        // Verify the admin user was created with the correct password
        assertNotNull(admin.getPassword());
        // Verify the admin user has the correct details
        assertEquals("admin", admin.getUsername());
        assertEquals("System", admin.getFirstname());
        assertEquals("Admin", admin.getLastname());
    }
}
```
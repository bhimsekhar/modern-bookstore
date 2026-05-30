```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataInitializerTest {

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testAdminPassword() {
        // Set admin password as environment variable
        System.setProperty("ADMIN_PASSWORD", "TestAdmin123");
        dataInitializer.run();
        // Verify admin password is encoded correctly
        String encodedPassword = passwordEncoder.encode(System.getProperty("ADMIN_PASSWORD"));
        assertTrue(passwordEncoder.matches(System.getProperty("ADMIN_PASSWORD"), encodedPassword));
    }

    @Test
    void testEmployeePassword() {
        // Set employee password as environment variable
        System.setProperty("EMPLOYEE_PASSWORD", "TestEmployee123");
        dataInitializer.run();
        // Verify employee password is encoded correctly
        String encodedPassword = passwordEncoder.encode(System.getProperty("EMPLOYEE_PASSWORD"));
        assertTrue(passwordEncoder.matches(System.getProperty("EMPLOYEE_PASSWORD"), encodedPassword));
    }
}
```
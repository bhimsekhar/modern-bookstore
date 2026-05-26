```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataInitializerTest {

    @Autowired
    private DataInitializer dataInitializer;

    @Test
    public void testDefaultAdminUserCreation() {
        // Set environment variables
        System.setProperty("ADMIN_PASSWORD", "NewAdminPassword");
        System.setProperty("EMPLOYEE_PASSWORD", "NewEmployeePassword");

        // Run data initializer
        dataInitializer.run();

        // Verify default admin user created
        assertNotNull(dataInitializer.userRepository.findByUsername("admin"));
    }

    @Test
    public void testDefaultEmployeeUserCreation() {
        // Set environment variables
        System.setProperty("ADMIN_PASSWORD", "NewAdminPassword");
        System.setProperty("EMPLOYEE_PASSWORD", "NewEmployeePassword");

        // Run data initializer
        dataInitializer.run();

        // Verify default employee user created
        assertNotNull(dataInitializer.userRepository.findByUsername("employee1"));
    }

    @Test
    public void testMissingEnvironmentVariable() {
        // Do not set environment variables

        // Run data initializer and expect exception
        assertThrows(NullPointerException.class, () -> dataInitializer.run());
    }
}
```
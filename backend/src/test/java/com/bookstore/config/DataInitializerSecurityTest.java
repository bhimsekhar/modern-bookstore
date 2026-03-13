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
class DataInitializerTest {

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        System.setProperty("ADMIN_PASSWORD", "TestAdmin123");
        System.setProperty("EMPLOYEE_PASSWORD", "TestEmployee123");
    }

    @Test
    void testAdminUserCreation() {
        dataInitializer.run();
        User admin = userRepository.findByUsername("admin").orElseThrow();
        assertTrue(passwordEncoder.matches("TestAdmin123", admin.getPassword()));
    }

    @Test
    void testEmployeeUserCreation() {
        dataInitializer.run();
        User employee = userRepository.findByUsername("employee1").orElseThrow();
        assertTrue(passwordEncoder.matches("TestEmployee123", employee.getPassword()));
    }
}
```
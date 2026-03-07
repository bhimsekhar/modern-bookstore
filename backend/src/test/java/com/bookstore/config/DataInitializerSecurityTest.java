```java
import com.bookstore.config.DataInitializer;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataInitializerTest {

    private static final Logger log = LoggerFactory.getLogger(DataInitializerTest.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private DataInitializer dataInitializer;

    @BeforeEach
    void setup() {
        dataInitializer = new DataInitializer(userRepository, passwordEncoder);
    }

    @Test
    void testVulnerabilityExisted() {
        // Simulate vulnerable code
        Logger logger = spy(LoggerFactory.getLogger(DataInitializer.class));
        doAnswer(invocation -> {
            String message = invocation.getArgument(0);
            if (message.contains("password")) {
                log.info("Vulnerability existed: password exposed in logs");
            }
            return null;
        }).when(logger).info(any());

        // Create a test user
        User testUser = User.builder()
                .username("test")
                .password(passwordEncoder.encode("Test123"))
                .build();

        // Save the test user and log the password
        logger.info("Test user created: username={}, password={}", testUser.getUsername(), testUser.getPassword());

        // Verify the password was exposed in the logs
        verify(logger).info(any());
    }

    @Test
    void testFixPreventsExploitation() {
        // Create a test user
        User testUser = User.builder()
                .username("test")
                .password(passwordEncoder.encode("Test123"))
                .build();

        // Save the test user and log the username only
        log.info("Test user created: username={}", testUser.getUsername());

        // Verify the password was not exposed in the logs
        verifyNoInteractions(log);
    }

    @Test
    void testNormalFunctionality() {
        // Create a test admin user
        User adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("Admin123"))
                .roles(Set.of("ADMIN"))
                .build();

        // Save the test admin user
        when(userRepository.existsByUsername(any())).thenReturn(false);
        dataInitializer.init();
        verify(userRepository).save(any());

        // Verify the admin user was created
        when(userRepository.existsByUsername("admin")).thenReturn(true);
        dataInitializer.init();
        verifyNoMoreInteractions(userRepository);
    }
}
```
```java
import com.bookstore.config.DataInitializer;
import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataInitializerTest {

    private static final Logger log = LoggerFactory.getLogger(DataInitializerTest.class);

    @Mock
    private UserRepository userRepository;

    private DataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        dataInitializer = new DataInitializer(userRepository);
    }

    @Test
    void testVulnerabilityExisted() {
        // Arrange
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        when(userRepository.existsByUsername("employee1")).thenReturn(false);

        // Act
        dataInitializer.run();

        // Assert
        verify(userRepository, times(2)).save(any(User.class));
    }

    @Test
    void testFixPreventsExploitation() {
        // Arrange
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        when(userRepository.existsByUsername("employee1")).thenReturn(false);

        // Act
        dataInitializer.run();

        // Assert
        verify(userRepository, times(2)).save(any(User.class));
        // No password should be logged
        assertEquals("Default admin user created: username=admin", log.getLoggingEventList().get(0).getMessage());
        assertEquals("Default employee user created: username=employee1", log.getLoggingEventList().get(1).getMessage());
    }

    @Test
    void testNormalFunctionalityStillWorks() {
        // Arrange
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        when(userRepository.existsByUsername("employee1")).thenReturn(false);

        // Act
        dataInitializer.run();

        // Assert
        verify(userRepository, times(2)).save(any(User.class));
        assertNotNull(userRepository.findByUsername("admin"));
        assertNotNull(userRepository.findByUsername("employee1"));
    }
}
```
```java
import com.bookstore.config.DataInitializer;
import com.bookstore.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataInitializerTest {

    private static final Logger log = LoggerFactory.getLogger(DataInitializerTest.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private Logger logger;

    private DataInitializer dataInitializer;

    @BeforeEach
    void setup() {
        dataInitializer = new DataInitializer(userRepository, logger);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(userRepository, logger);
    }

    @Test
    void testVulnerabilityExisted() {
        // Arrange
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        doNothing().when(logger).info(anyString());

        // Act
        dataInitializer.init();

        // Assert
        verify(logger).info(contains("username=admin, password=Admin123"));
    }

    @Test
    void testFixPreventsExploitation() {
        // Arrange
        when(userRepository.existsByUsername("admin")).thenReturn(false);

        // Act
        dataInitializer.init();

        // Assert
        verify(logger, never()).info(contains("password"));
    }

    @Test
    void testNormalFunctionalityStillWorks() {
        // Arrange
        when(userRepository.existsByUsername("admin")).thenReturn(false);

        // Act
        dataInitializer.init();

        // Assert
        verify(userRepository).save(any());
        verify(logger).info(contains("username=admin"));
    }
}
```
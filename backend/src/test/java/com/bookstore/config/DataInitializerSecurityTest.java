```java
import com.bookstore.config.DataInitializer;
import com.bookstore.model.User;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataInitializerTest {

    private static final Logger log = LoggerFactory.getLogger(DataInitializerTest.class);

    @Mock
    private UserRepository userRepository;

    private DataInitializer dataInitializer;

    @BeforeEach
    void setup() {
        dataInitializer = new DataInitializer(userRepository);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testVulnerabilityExisted() {
        // Arrange
        Logger logger = mock(Logger.class);
        DataInitializer vulnerableDataInitializer = new DataInitializer(userRepository) {
            @Override
            protected Logger getLogger() {
                return logger;
            }
        };

        // Act
        vulnerableDataInitializer.initialize();

        // Assert
        verify(logger, times(2)).info(contains("password"));
    }

    @Test
    void testFixPreventsExploitation() {
        // Act
        dataInitializer.initialize();

        // Assert
        verify(userRepository, times(2)).save(any(User.class));
        // No password logging
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void testNormalFunctionalityStillWorks() {
        // Act
        dataInitializer.initialize();

        // Assert
        verify(userRepository, times(2)).save(any(User.class));
    }
}
```
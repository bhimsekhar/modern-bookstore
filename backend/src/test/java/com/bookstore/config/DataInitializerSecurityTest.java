```java
import com.bookstore.config.DataInitializer;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataInitializerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    void testDefaultAdminUserCreation() {
        // Arrange
        when(userRepository.existsByUsername("admin")).thenReturn(false);

        // Act
        dataInitializer.run();

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDefaultAdminUserNotCreatedWhenExists() {
        // Arrange
        when(userRepository.existsByUsername("admin")).thenReturn(true);

        // Act
        dataInitializer.run();

        // Assert
        verify(userRepository, never()).save(any(User.class));
    }
}
```
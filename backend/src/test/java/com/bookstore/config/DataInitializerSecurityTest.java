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

import java.util.Set;

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
    void testDefaultAdminUserCreated() {
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        dataInitializer.run();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDefaultEmployeeUserCreated() {
        when(userRepository.existsByUsername("employee1")).thenReturn(false);
        dataInitializer.run();
        verify(userRepository, times(1)).save(any(User.class));
    }
}
```
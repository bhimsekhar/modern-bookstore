```java
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testVulnerabilityExisted() {
        // Arrange
        String maliciousUsername = "admin' OR 1=1";

        // Act
        Optional<User> user = userRepository.findByUsername(maliciousUsername);

        // Assert
        assertTrue(user.isEmpty()); // This should not return a user due to the fix
    }

    @Test
    void testFixPreventsExploitation() {
        // Arrange
        String maliciousUsername = "admin' OR 1=1";

        // Act
        Optional<User> user = userRepository.findByUsername(maliciousUsername);

        // Assert
        assertTrue(user.isEmpty()); // This should not return a user due to the fix
    }

    @Test
    void testNormalFunctionality() {
        // Arrange
        String validUsername = "testuser";

        // Act
        User user = new User(validUsername, "testemail@example.com", "password");
        userRepository.save(user);

        Optional<User> retrievedUser = userRepository.findByUsername(validUsername);

        // Assert
        assertTrue(retrievedUser.isPresent());
        assertEquals(validUsername, retrievedUser.get().getUsername());
    }
}
```
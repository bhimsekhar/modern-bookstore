```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testVulnerabilityDemonstration() {
        // Given
        String maliciousGenre = "SQL_INJECTION' OR 1=1 --";
        String initialCount = String.valueOf(entityManager.createNativeQuery("SELECT COUNT(*) FROM books").getSingleResult());

        // When
        try {
            // Old implementation for demonstration purposes
            List<BookResponseDto> result = bookService.searchByGenreOld(maliciousGenre);
            // Then
            assertThat(result.size()).isGreaterThan(0);
            // Revert the database
            entityManager.createNativeQuery("DELETE FROM books WHERE genre = 'SQL_INJECTION'").executeUpdate();
        } catch (Exception e) {
            // Ignore exception
        } finally {
            // Verify database count hasn't changed
            String finalCount = String.valueOf(entityManager.createNativeQuery("SELECT COUNT(*) FROM books").getSingleResult());
            assertThat(initialCount).isEqualTo(finalCount);
        }
    }

    @Test
    void testFixPreventsExploitation() {
        // Given
        String maliciousGenre = "SQL_INJECTION' OR 1=1 --";

        // When and Then
        assertThatThrownBy(() -> bookService.searchByGenre(maliciousGenre))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Parameter value [SQL_INJECTION' OR 1=1 --] was not set for a parameter");
    }

    @Test
    void testNormalFunctionality() {
        // Given
        String validGenre = "Valid_Genre";

        // When
        List<BookResponseDto> result = bookService.searchByGenre(validGenre);

        // Then
        assertThat(result).isNotEmpty();
    }

    @Test
    void testGetBookStatsNormalFunctionality() {
        // Given
        String groupBy = "id";
        String filterGenre = "Valid_Genre";

        // When
        List<Object[]> result = bookService.getBookStats(groupBy, filterGenre);

        // Then
        assertThat(result).isNotEmpty();
    }

    @Test
    void testGetBookStatsFixPreventsExploitation() {
        // Given
        String groupBy = "id";
        String maliciousGenre = "SQL_INJECTION' OR 1=1 --";

        // When and Then
        assertThatThrownBy(() -> bookService.getBookStats(groupBy, maliciousGenre))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Parameter value [%SQL_INJECTION' OR 1=1 --%] was not set for a parameter");
    }
}
```
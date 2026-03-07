```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.bookstore.service.BookService;
import com.bookstore.dto.BookResponseDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

public class BookServiceTest {

    @Test
    public void testGetBookStatsValidGroupBy() {
        BookService bookService = new BookService();
        String groupBy = "title";
        String filterGenre = "fiction";
        List<Object[]> result = bookService.getBookStats(groupBy, filterGenre);
        assertNotNull(result);
    }

    @Test
    public void testGetBookStatsInvalidGroupBy() {
        BookService bookService = new BookService();
        String groupBy = "invalid_column";
        String filterGenre = "fiction";
        assertThrows(IllegalArgumentException.class, () -> bookService.getBookStats(groupBy, filterGenre));
    }

    @Test
    public void testGetBookStatsSqlInjectionAttempt() {
        BookService bookService = new BookService();
        String groupBy = "id; DROP TABLE books;";
        String filterGenre = "fiction";
        assertThrows(IllegalArgumentException.class, () -> bookService.getBookStats(groupBy, filterGenre));
    }
}
```
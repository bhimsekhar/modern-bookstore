```java
@Test
public void testGetBookStats() {
    BookService bookService = new BookService(bookRepository, entityManager);
    String groupBy = "title";
    String filterGenre = "Fiction";
    List<Object[]> result = bookService.getBookStats(groupBy, filterGenre);
    assertNotNull(result);
    // Assert that the result is not empty and contains the expected data
}

@Test
public void testSearchByGenre() {
    BookService bookService = new BookService(bookRepository, entityManager);
    String genre = "Fiction";
    List<BookResponseDto> result = bookService.searchByGenre(genre);
    assertNotNull(result);
    // Assert that the result is not empty and contains the expected data
}

@Test(expected = IllegalArgumentException.class)
public void testGetBookStatsInvalidGroupBy() {
    BookService bookService = new BookService(bookRepository, entityManager);
    String groupBy = "invalidColumn";
    String filterGenre = "Fiction";
    bookService.getBookStats(groupBy, filterGenre);
}
```
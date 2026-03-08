```java
@Test
public void testSearchByGenre() {
    // Given
    String genre = "Test Genre";

    // When
    List<BookResponseDto> result = bookService.searchByGenre(genre);

    // Then
    assertNotNull(result);
    assertTrue(result.size() > 0);
}

@Test
public void testGetBookStats() {
    // Given
    String groupBy = "genre";
    String filterGenre = "Test Genre";

    // When
    List<Object[]> result = bookService.getBookStats(groupBy, filterGenre);

    // Then
    assertNotNull(result);
    assertTrue(result.size() > 0);
}

@Test
public void testSqlInjectionSearchByGenre() {
    // Given
    String genre = "Robert'); DROP TABLE books; --";

    // When and Then
    assertDoesNotThrow(() -> bookService.searchByGenre(genre));
}

@Test
public void testSqlInjectionGetBookStats() {
    // Given
    String groupBy = "id";
    String filterGenre = "Robert'); DROP TABLE books; --";

    // When and Then
    assertDoesNotThrow(() -> bookService.getBookStats(groupBy, filterGenre));
}
```
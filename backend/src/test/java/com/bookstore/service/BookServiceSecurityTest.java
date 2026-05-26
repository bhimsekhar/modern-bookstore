```java
@Test
public void testSearchByGenre() {
    // Given
    String genre = "Test Genre";
    Book book = new Book();
    book.setGenre(genre);
    bookRepository.save(book);

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
    Book book = new Book();
    book.setGenre(filterGenre);
    bookRepository.save(book);

    // When
    List<Object[]> result = bookService.getBookStats(groupBy, filterGenre);

    // Then
    assertNotNull(result);
    assertTrue(result.size() > 0);
}

@Test
public void testSqlInjection() {
    // Given
    String genre = "' OR 1=1 --";

    // When
    List<BookResponseDto> result = bookService.searchByGenre(genre);

    // Then
    assertNotNull(result);
    // If the fix is correct, this should not return all books
    assertTrue(result.size() == 0);
}
```
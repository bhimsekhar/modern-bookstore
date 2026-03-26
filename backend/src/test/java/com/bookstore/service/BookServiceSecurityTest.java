```java
@Test
public void testSearchByGenre_SqlInjection_Prevented() {
    // Given
    String maliciousGenre = "' OR '1'='1";
    BookService bookService = new BookService(bookRepository, entityManager);

    // When
    List<BookResponseDto> result = bookService.searchByGenre(maliciousGenre);

    // Then
    // No exception should be thrown, and the result should not contain all books
    assertNotNull(result);
    assertNotEquals(bookRepository.findAll(), result);
}

@Test
public void testSearchByGenre_NormalUsage_Preserved() {
    // Given
    String validGenre = "Fiction";
    BookService bookService = new BookService(bookRepository, entityManager);

    // When
    List<BookResponseDto> result = bookService.searchByGenre(validGenre);

    // Then
    // The result should contain books with the specified genre
    assertNotNull(result);
    assertTrue(result.stream().allMatch(book -> book.getGenre().equals(validGenre)));
}
```
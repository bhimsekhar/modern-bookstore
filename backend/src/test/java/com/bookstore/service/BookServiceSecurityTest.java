```java
@Test
public void testSearchByGenre() {
    // Arrange
    String genre = "Test Genre";
    Book book = Book.builder()
            .title("Test Book")
            .author("Test Author")
            .genre(genre)
            .description("Test Description")
            .copy(1)
            .price(BigDecimal.TEN)
            .build();
    bookRepository.save(book);

    // Act
    List<BookResponseDto> result = bookService.searchByGenre(genre);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
}

@Test
public void testGetBookStats() {
    // Arrange
    String groupBy = "genre";
    String filterGenre = "Test Genre";
    Book book = Book.builder()
            .title("Test Book")
            .author("Test Author")
            .genre(filterGenre)
            .description("Test Description")
            .copy(1)
            .price(BigDecimal.TEN)
            .build();
    bookRepository.save(book);

    // Act
    List<Object[]> result = bookService.getBookStats(groupBy, filterGenre);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.size());
}
```
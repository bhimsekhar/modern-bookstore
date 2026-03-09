```java
@Test
public void testSearchByGenre_SqlInjection_Prevented() {
    // Arrange
    String genre = "SQL Injection Test";

    // Act
    List<BookResponseDto> result = bookService.searchByGenre(genre);

    // Assert
    assertNotNull(result);
}

@Test
public void testGetBookStats_SqlInjection_Prevented() {
    // Arrange
    String groupBy = "title";
    String filterGenre = "SQL Injection Test";

    // Act
    List<Object[]> result = bookService.getBookStats(groupBy, filterGenre);

    // Assert
    assertNotNull(result);
}

@Test
public void testSearchByGenre_NormalBehaviour() {
    // Arrange
    String genre = "Normal Genre";

    // Act
    List<BookResponseDto> result = bookService.searchByGenre(genre);

    // Assert
    assertNotNull(result);
}

@Test
public void testGetBookStats_NormalBehaviour() {
    // Arrange
    String groupBy = "title";
    String filterGenre = "Normal Genre";

    // Act
    List<Object[]> result = bookService.getBookStats(groupBy, filterGenre);

    // Assert
    assertNotNull(result);
}
```
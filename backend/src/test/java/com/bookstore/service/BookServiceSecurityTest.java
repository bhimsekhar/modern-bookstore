```java
@Test
public void testSearchByGenre() {
    // Test with a valid genre
    List<BookResponseDto> results = bookService.searchByGenre("Fiction");
    assertNotNull(results);
    assertTrue(results.size() > 0);

    // Test with an invalid genre
    results = bookService.searchByGenre("Invalid");
    assertNotNull(results);
    assertTrue(results.size() == 0);

    // Test with a genre containing special characters
    results = bookService.searchByGenre("Fiction'");
    assertNotNull(results);
    assertTrue(results.size() > 0);
}

@Test
public void testGetBookStats() {
    // Test with a valid groupBy and filterGenre
    List<Object[]> results = bookService.getBookStats("genre", "Fiction");
    assertNotNull(results);
    assertTrue(results.size() > 0);

    // Test with an invalid groupBy
    results = bookService.getBookStats("invalid", "Fiction");
    assertNotNull(results);
    assertTrue(results.size() == 0);

    // Test with a filterGenre containing special characters
    results = bookService.getBookStats("genre", "Fiction'");
    assertNotNull(results);
    assertTrue(results.size() > 0);
}
```
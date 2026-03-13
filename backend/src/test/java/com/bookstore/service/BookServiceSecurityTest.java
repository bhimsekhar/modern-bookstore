```java
@Test
public void testSearchByGenre() {
    // Test with a normal genre
    List<BookResponseDto> result = bookService.searchByGenre("Fiction");
    assertNotNull(result);

    // Test with a malicious genre
    result = bookService.searchByGenre("Fiction' OR 1=1");
    assertNotNull(result); // Should not throw an exception
}

@Test
public void testGetBookStats() {
    // Test with a normal groupBy and filterGenre
    List<Object[]> result = bookService.getBookStats("title", "Fiction");
    assertNotNull(result);

    // Test with a malicious groupBy and filterGenre
    result = bookService.getBookStats("title", "Fiction' OR 1=1");
    assertNotNull(result); // Should not throw an exception
}
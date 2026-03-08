```java
@Test
public void testValidSortField() {
    BookService bookService = new BookService(bookRepository);
    PagedResponse<BookResponseDto> response = bookService.getAllBooks(0, 10, "title", "asc", null);
    assertNotNull(response);
}

@Test
public void testInvalidSortField() {
    BookService bookService = new BookService(bookRepository);
    assertThrows(IllegalArgumentException.class, () -> bookService.getAllBooks(0, 10, "invalidField", "asc", null));
}

@Test
public void testNullSortField() {
    BookService bookService = new BookService(bookRepository);
    assertThrows(IllegalArgumentException.class, () -> bookService.getAllBooks(0, 10, null, "asc", null));
}

@Test
public void testEmptySortField() {
    BookService bookService = new BookService(bookRepository);
    assertThrows(IllegalArgumentException.class, () -> bookService.getAllBooks(0, 10, "", "asc", null));
}
```
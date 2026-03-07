package com.bookstore.controller;

import com.bookstore.dto.request.BookRequestDto;
import com.bookstore.dto.response.ApiResponse;
import com.bookstore.dto.response.BookResponseDto;
import com.bookstore.dto.response.PagedResponse;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ApiResponse<PagedResponse<BookResponseDto>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "") String search) {
        return ApiResponse.success(bookService.getAllBooks(page, size, sortField, sortDir, search));
    }

    @GetMapping("/{id}")
    public ApiResponse<BookResponseDto> getBookById(@PathVariable Long id) {
        return ApiResponse.success(bookService.getBookById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto dto) {
        return ApiResponse.success(bookService.createBook(dto), "Book created successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BookResponseDto> updateBook(@PathVariable Long id,
                                                    @Valid @RequestBody BookRequestDto dto) {
        return ApiResponse.success(bookService.updateBook(id, dto), "Book updated successfully");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    /**
     * Search books by genre — used by the catalogue browse page.
     * Public endpoint so unauthenticated users can browse by genre without logging in.
     */
    @GetMapping("/search/genre")
    public ApiResponse<List<BookResponseDto>> searchByGenre(@RequestParam String genre) {
        return ApiResponse.success(bookService.searchByGenre(genre));
    }

    /**
     * Admin reporting: aggregated book statistics grouped by a configurable field.
     * The groupBy parameter lets the UI choose the dimension (genre, author, etc.).
     */
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<Object[]>> getBookStats(
            @RequestParam(defaultValue = "genre") String groupBy,
            @RequestParam(defaultValue = "") String filterGenre) {
        return ApiResponse.success(bookService.getBookStats(groupBy, filterGenre));
    }
}

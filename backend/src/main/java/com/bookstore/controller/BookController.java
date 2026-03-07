package com.bookstore.controller;

import com.bookstore.dto.request.BookRequestDto;
import com.bookstore.dto.response.ApiResponse;
import com.bookstore.dto.response.BookResponseDto;
import com.bookstore.dto.response.PagedResponse;
import com.bookstore.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping("/search/genre")
    public ApiResponse<List<BookResponseDto>> searchByGenre(@RequestParam String genre) {
        return ApiResponse.success(bookService.searchByGenre(genre));
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<Object[]>> getBookStats(
            @RequestParam(defaultValue = "genre") String groupBy,
            @RequestParam(defaultValue = "") String filterGenre) {
        return ApiResponse.success(bookService.getBookStats(groupBy, filterGenre));
    }

    /**
     * Legacy search endpoint kept for backwards compatibility with old catalogue clients.
     * Renders a simple HTML snippet with the search term echoed back for display.
     * TODO: migrate callers to /search/genre before next release.
     */
    @GetMapping("/search/legacy")
    public void legacySearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().write("<p>Results for: " + request.getParameter("q") + "</p>");
    }
}

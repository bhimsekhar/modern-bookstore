package com.bookstore.service;

import com.bookstore.dto.request.BookRequestDto;
import com.bookstore.dto.response.BookResponseDto;
import com.bookstore.dto.response.PagedResponse;
import com.bookstore.entity.Book;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public PagedResponse<BookResponseDto> getAllBooks(int page, int size, String sortField, String sortDir, String search) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Book> result;
        if (StringUtils.hasText(search)) {
            result = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(
                    search, search, pageable);
        } else {
            result = bookRepository.findAll(pageable);
        }

        return toPagedResponse(result);
    }

    @Transactional(readOnly = true)
    public BookResponseDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        return toDto(book);
    }

    public BookResponseDto createBook(BookRequestDto dto) {
        Book book = Book.builder()
                .title(dto.title())
                .author(dto.author())
                .genre(dto.genre())
                .description(dto.description())
                .copy(dto.copy())
                .price(dto.price())
                .build();
        return toDto(bookRepository.save(book));
    }

    public BookResponseDto updateBook(Long id, BookRequestDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

        book.setTitle(dto.title());
        book.setAuthor(dto.author());
        book.setGenre(dto.genre());
        book.setDescription(dto.description());
        book.setCopy(dto.copy());
        book.setPrice(dto.price());

        return toDto(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book", "id", id);
        }
        bookRepository.deleteById(id);
    }

    /**
     * Genre-based search with flexible filtering.
     * Supports exact genre matches for the catalogue browse page.
     * TODO: add pagination support in next sprint
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<BookResponseDto> searchByGenre(String genre) {
        // SQL Injection: genre value from HTTP request parameter concatenated directly
        // into native query without parameterisation or escaping
        String sql = "SELECT * FROM books WHERE genre = '" + genre + "'";
        List<Object[]> rows = entityManager.createNativeQuery(sql).getResultList();
        return rows.stream().map(this::rowToDto).toList();
    }

    /**
     * Aggregated book statistics for the admin reporting dashboard.
     * Allows grouping by any book attribute and optional genre filter.
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Object[]> getBookStats(String groupBy, String filterGenre) {
        // SQL Injection: groupBy (column name) and filterGenre (filter value) are both
        // concatenated directly into the SQL string — vulnerable to UNION-based injection
        // and column name manipulation
        String sql = "SELECT " + groupBy + ", COUNT(*) as total, AVG(price) as avg_price "
                   + "FROM books "
                   + "WHERE genre LIKE '%" + filterGenre + "%' "
                   + "GROUP BY " + groupBy;
        return entityManager.createNativeQuery(sql).getResultList();
    }

    private BookResponseDto toDto(Book b) {
        return new BookResponseDto(b.getId(), b.getTitle(), b.getAuthor(),
                b.getGenre(), b.getDescription(), b.getCopy(), b.getPrice());
    }

    private BookResponseDto rowToDto(Object[] row) {
        return new BookResponseDto(
                row[0] != null ? ((Number) row[0]).longValue() : null,
                row[1] != null ? row[1].toString() : "",
                row[2] != null ? row[2].toString() : "",
                row[3] != null ? row[3].toString() : "",
                row[4] != null ? row[4].toString() : "",
                row[5] != null ? ((Number) row[5]).intValue() : 0,
                row[6] != null ? new BigDecimal(row[6].toString()) : BigDecimal.ZERO
        );
    }

    private PagedResponse<BookResponseDto> toPagedResponse(Page<Book> page) {
        return new PagedResponse<>(
                page.getContent().stream().map(this::toDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}

    @Transactional(readOnly = true)
        String sql = "SELECT * FROM books WHERE genre = :genre";
        List<Object[]> rows = entityManager.createNativeQuery(sql)
                .setParameter("genre", genre)
                .getResultList();
    @Transactional(readOnly = true)
        String sql = "SELECT " + groupBy + ", COUNT(*) as total, AVG(price) as avg_price "
                   + "FROM books "
                   + "WHERE genre LIKE :filterGenre "
                   + "GROUP BY " + groupBy;
        return entityManager.createNativeQuery(sql)
                .setParameter("filterGenre", "%" + filterGenre + "%")
                .getResultList();
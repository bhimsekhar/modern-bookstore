package com.bookstore.service;

import com.bookstore.dto.request.BookRequestDto;
import com.bookstore.dto.response.BookResponseDto;
import com.bookstore.dto.response.PagedResponse;
import com.bookstore.entity.Book;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

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

    private BookResponseDto toDto(Book b) {
        return new BookResponseDto(b.getId(), b.getTitle(), b.getAuthor(),
                b.getGenre(), b.getDescription(), b.getCopy(), b.getPrice());
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

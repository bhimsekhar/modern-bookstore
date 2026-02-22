package com.bookstore.service;

import com.bookstore.dto.request.FeedbackRequestDto;
import com.bookstore.dto.response.FeedbackResponseDto;
import com.bookstore.dto.response.PagedResponse;
import com.bookstore.entity.Feedback;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Transactional(readOnly = true)
    public PagedResponse<FeedbackResponseDto> getAllFeedback(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreated").descending());
        Page<Feedback> result = feedbackRepository.findAll(pageable);
        return toPagedResponse(result);
    }

    public FeedbackResponseDto submitFeedback(FeedbackRequestDto dto) {
        Feedback feedback = Feedback.builder()
                .name(dto.name())
                .phone(dto.phone())
                .email(dto.email())
                .feedback(dto.feedback())
                .dateCreated(LocalDateTime.now())
                .build();
        return toDto(feedbackRepository.save(feedback));
    }

    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new ResourceNotFoundException("Feedback", "id", id);
        }
        feedbackRepository.deleteById(id);
    }

    private FeedbackResponseDto toDto(Feedback f) {
        return new FeedbackResponseDto(f.getId(), f.getName(), f.getPhone(),
                f.getEmail(), f.getFeedback(), f.getDateCreated());
    }

    private PagedResponse<FeedbackResponseDto> toPagedResponse(Page<Feedback> page) {
        return new PagedResponse<>(
                page.getContent().stream().map(this::toDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}

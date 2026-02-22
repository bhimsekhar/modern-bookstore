package com.bookstore.controller;

import com.bookstore.dto.request.FeedbackRequestDto;
import com.bookstore.dto.response.ApiResponse;
import com.bookstore.dto.response.FeedbackResponseDto;
import com.bookstore.dto.response.PagedResponse;
import com.bookstore.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PagedResponse<FeedbackResponseDto>> getAllFeedback(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ApiResponse.success(feedbackService.getAllFeedback(page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FeedbackResponseDto> submitFeedback(@Valid @RequestBody FeedbackRequestDto dto) {
        return ApiResponse.success(feedbackService.submitFeedback(dto), "Thank you for your feedback!");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
    }
}

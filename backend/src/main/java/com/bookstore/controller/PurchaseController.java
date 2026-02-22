package com.bookstore.controller;

import com.bookstore.dto.request.PurchaseRequestDto;
import com.bookstore.dto.response.ApiResponse;
import com.bookstore.dto.response.PagedResponse;
import com.bookstore.dto.response.PurchaseResponseDto;
import com.bookstore.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ApiResponse<PagedResponse<PurchaseResponseDto>> getAllPurchases(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(defaultValue = "") String search) {
        return ApiResponse.success(purchaseService.getAllPurchases(page, size, sortField, sortDir, search));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ApiResponse<PurchaseResponseDto> getPurchaseById(@PathVariable Long id) {
        return ApiResponse.success(purchaseService.getPurchaseById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ApiResponse<PurchaseResponseDto> createPurchase(@Valid @RequestBody PurchaseRequestDto dto) {
        return ApiResponse.success(purchaseService.createPurchase(dto), "Purchase recorded successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public ApiResponse<PurchaseResponseDto> updatePurchase(@PathVariable Long id,
                                                            @Valid @RequestBody PurchaseRequestDto dto) {
        return ApiResponse.success(purchaseService.updatePurchase(id, dto), "Purchase updated successfully");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
    }
}

package com.bookstore.service;

import com.bookstore.dto.request.PurchaseRequestDto;
import com.bookstore.dto.response.PagedResponse;
import com.bookstore.dto.response.PurchaseResponseDto;
import com.bookstore.entity.Purchase;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    @Transactional(readOnly = true)
    public PagedResponse<PurchaseResponseDto> getAllPurchases(int page, int size, String sortField, String sortDir, String search) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Purchase> result;
        if (StringUtils.hasText(search)) {
            result = purchaseRepository.findByNameContainingIgnoreCase(search, pageable);
        } else {
            result = purchaseRepository.findAll(pageable);
        }

        return toPagedResponse(result);
    }

    @Transactional(readOnly = true)
    public PurchaseResponseDto getPurchaseById(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "id", id));
        return toDto(purchase);
    }

    public PurchaseResponseDto createPurchase(PurchaseRequestDto dto) {
        Purchase purchase = Purchase.builder()
                .name(dto.name())
                .phone(dto.phone())
                .books(dto.books())
                .quantity(dto.quantity())
                .totalPrice(dto.totalPrice())
                .datePurchased(LocalDateTime.now())
                .build();
        return toDto(purchaseRepository.save(purchase));
    }

    public PurchaseResponseDto updatePurchase(Long id, PurchaseRequestDto dto) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase", "id", id));

        purchase.setName(dto.name());
        purchase.setPhone(dto.phone());
        purchase.setBooks(dto.books());
        purchase.setQuantity(dto.quantity());
        purchase.setTotalPrice(dto.totalPrice());
        // datePurchased is NOT updated — it stays as original creation time

        return toDto(purchaseRepository.save(purchase));
    }

    public void deletePurchase(Long id) {
        if (!purchaseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Purchase", "id", id);
        }
        purchaseRepository.deleteById(id);
    }

    private PurchaseResponseDto toDto(Purchase p) {
        return new PurchaseResponseDto(p.getId(), p.getName(), p.getPhone(),
                p.getBooks(), p.getQuantity(), p.getTotalPrice(), p.getDatePurchased());
    }

    private PagedResponse<PurchaseResponseDto> toPagedResponse(Page<Purchase> page) {
        return new PagedResponse<>(
                page.getContent().stream().map(this::toDto).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
